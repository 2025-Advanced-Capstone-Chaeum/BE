package com.chaeum.api.domain.friendship.repository;

import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.friendship.entity.FriendshipStatus;
import com.chaeum.api.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsBySenderAndReceiverAndStatus(Member sender, Member receiver, FriendshipStatus status);

    boolean existsBySenderAndReceiver(Member sender, Member receiver);

    List<Friendship> findBySenderOrReceiverAndStatus(Member sender, Member receiver, FriendshipStatus status);
}
