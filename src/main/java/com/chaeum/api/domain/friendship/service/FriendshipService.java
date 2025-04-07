package com.chaeum.api.domain.friendship.service;

import com.chaeum.api.domain.donation.service.DonationService;
import com.chaeum.api.domain.friendship.dto.request.FriendshipCreateRequest;
import com.chaeum.api.domain.friendship.dto.request.FriendshipUpdateRequest;
import com.chaeum.api.domain.friendship.dto.response.FriendshipResponse;
import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.friendship.entity.FriendshipStatus;
import com.chaeum.api.domain.friendship.repository.FriendshipRepository;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.member.service.MemberService;
import com.chaeum.api.domain.title.entity.TitleName;
import com.chaeum.api.domain.title.service.TitleService;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.pagination.cursorResult.IdCursorResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final LoginMemberProvider loginMemberProvider;
    private final TitleService titleService;
    private final MemberService memberService;
    private final DonationService donationService;

    @Transactional
    public Long save(FriendshipCreateRequest friendshipCreateRequest) {
        Member sender = loginMemberProvider.getCurrentLoginMember();
        Member receiver = memberService.findById(friendshipCreateRequest.getFriendshipId());
        validateNotSelfRequest(sender, receiver);
        validateDuplicateFriendship(sender, receiver);
        Friendship friendship = Friendship.create(sender, receiver, friendshipCreateRequest.getFriendshipStatus());
        friendshipRepository.save(friendship);
        return friendship.getId();
    }

    @Transactional
    public FriendshipResponse getFriend(Long friendshipId) {
        Friendship friendship = findById(friendshipId);
        Member me = loginMemberProvider.getCurrentLoginMember();
        Member friend = friendship.getFriend();
        validateMyFriendship(me, friend);
        List<TitleName> activeTitleNames = titleService.getActiveTitleNames(friend);
        int sharedDonationCount = donationService.getCountSharedDonations(me, friend);
        return FriendshipResponse.toDto(friend, activeTitleNames, sharedDonationCount);
    }

    @Transactional(readOnly = true)
    public IdCursorResult<FriendshipResponse> getFriendshipsByCondition(String friendName, Long cursor, int limit) {
        Member me = loginMemberProvider.getCurrentLoginMember();

        List<Friendship> friendships = friendshipRepository.findAll().stream()
            .filter(friendship -> {
                Member friend = friendship.getFriendOf(me);
                return (friendName == null || friendName.isBlank()
                    || friend.getName().toLowerCase().contains(friendName.toLowerCase()))
                    && (cursor == null || friendship.getId() > cursor);
            })
            .sorted(Comparator.comparingLong(Friendship::getId))
            .collect(Collectors.toList());

        List<FriendshipResponse> responses = friendships.stream()
            .map(friendship -> {
                Member friend = friendship.getFriendOf(me);
                List<TitleName> titleNames = titleService.getActiveTitleNames(friend);
                int sharedDonationCount = donationService.getCountSharedDonations(me, friend);
                return FriendshipResponse.toDto(friend, titleNames, sharedDonationCount);
            })
            .collect(Collectors.toList());

        return IdCursorResult.of(responses, cursor, limit);
    }

    @Transactional
    public Long update(FriendshipUpdateRequest friendshipUpdateRequest) {
        Member member = loginMemberProvider.getCurrentLoginMember();
        Friendship friendship = findById(friendshipUpdateRequest.getFriendshipId());
        validateMyFriendship(member, friendship.getFriend());
        friendshipUpdateRequest.getFriendshipStatus().apply(friendship);
        return friendship.getId();
    }

    @Transactional
    public Long delete(Long friendshipId) {
        friendshipRepository.deleteById(friendshipId);
        return friendshipId;
    }

    private void validateNotSelfRequest(Member me, Member target) {
        if (me.getId().equals(target.getId())) {
            throw ChaeumException.from(ErrorCode.INVALID_SELF_FRIENDSHIP);
        }
    }

    private void validateDuplicateFriendship(Member me, Member friend) {
        boolean exists = friendshipRepository.existsByMemberAndFriend(me, friend)
            || friendshipRepository.existsByMemberAndFriend(friend, me);
        if (exists) {
            throw ChaeumException.from(ErrorCode.ALREADY_FRIENDSHIP_EXISTS);
        }
    }

    private void validateMyFriendship(Member me, Member friend) {
        boolean exists = friendshipRepository.existsByMemberAndFriendAndStatus(me, friend, FriendshipStatus.ACCEPTED)
            || friendshipRepository.existsByMemberAndFriendAndStatus(friend, me, FriendshipStatus.ACCEPTED);
        if (!exists) {
            throw ChaeumException.from(ErrorCode.FRIENDSHIP_NOT_FOUND);
        }
    }

    public Friendship findById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.FRIENDSHIP_NOT_FOUND));
    }
}
