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
        Member receiver = memberService.findById(friendshipCreateRequest.getReceiverId());

        validateNotSelfRequest(sender, receiver);
        validateDuplicateFriendship(sender, receiver);

        Friendship friendship = Friendship.create(sender, receiver);
        friendshipRepository.save(friendship);

        return friendship.getId();
    }

    @Transactional(readOnly = true)
    public FriendshipResponse getFriend(Long friendshipId) {
        Member current = loginMemberProvider.getCurrentLoginMember();
        Friendship friendship = findById(friendshipId);

        if (friendship.isParticipant(current)) {
            throw ChaeumException.from(ErrorCode.FORBIDDEN_FRIENDSHIP_ACCESS);
        }

        return toFriendshipResponse(current, friendship);
    }

    @Transactional(readOnly = true)
    public IdCursorResult<FriendshipResponse> getFriendshipsByName(String friendName, Long cursor, int limit) {
        Member current = loginMemberProvider.getCurrentLoginMember();

        List<Friendship> friendships = friendshipRepository
            .findBySenderOrReceiverAndStatus(current, current, FriendshipStatus.ACCEPTED).stream()
            .filter(friendship -> {
                Member friend = friendship.getFriendOf(current);
                return !friend.isSame(current) && (
                    friendName == null || friendName.isBlank()
                        || friend.getName().toLowerCase().contains(friendName.toLowerCase())
                ) && (cursor == null || friendship.getId() < cursor);
            })
            .sorted(Comparator.comparingLong(Friendship::getId).reversed())
            .collect(Collectors.toList());

        List<FriendshipResponse> responses = friendships.stream()
            .map(friendship -> toFriendshipResponse(current, friendship))
            .collect(Collectors.toList());

        return IdCursorResult.of(responses, cursor, limit);
    }

    @Transactional
    public Long update(FriendshipUpdateRequest friendshipUpdateRequest) {
        Member current = loginMemberProvider.getCurrentLoginMember();
        Friendship friendship = findById(friendshipUpdateRequest.getFriendshipId());
        friendship.updateStatus(current, friendshipUpdateRequest.getFriendshipStatus());
        return friendship.getId();
    }

    @Transactional
    public Long delete(Long friendshipId) {
        friendshipRepository.deleteById(friendshipId);
        return friendshipId;
    }

    private FriendshipResponse toFriendshipResponse(Member current, Friendship friendship) {
        Member friend = friendship.getFriendOf(current);
        String title = String.valueOf(titleService.getTitle(friend));
        int sharedDonationCount = donationService.getCountSharedDonations(current, friend);
        return FriendshipResponse.toDto(friend, title, sharedDonationCount);
    }

    private void validateNotSelfRequest(Member me, Member target) {
        if (me.isSame(target)) {
            throw ChaeumException.from(ErrorCode.INVALID_SELF_FRIENDSHIP);
        }
    }

    private void validateDuplicateFriendship(Member me, Member friend) {
        boolean exists = friendshipRepository.existsBySenderAndReceiver(me, friend)
            || friendshipRepository.existsBySenderAndReceiver(friend, me);
        if (exists) {
            throw ChaeumException.from(ErrorCode.ALREADY_FRIENDSHIP_EXISTS);
        }
    }

    public Friendship findById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
            .orElseThrow(() -> ChaeumException.from(ErrorCode.FRIENDSHIP_NOT_FOUND));
    }
}
