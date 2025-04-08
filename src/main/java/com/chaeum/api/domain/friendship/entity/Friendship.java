package com.chaeum.api.domain.friendship.entity;

import static com.chaeum.api.domain.friendship.entity.FriendshipStatus.ACCEPTED;
import static com.chaeum.api.domain.friendship.entity.FriendshipStatus.BLOCKED;
import static com.chaeum.api.domain.friendship.entity.FriendshipStatus.REJECTED;

import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.EnumSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "friendship")
public class Friendship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", nullable = false)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FriendshipStatus status;

    public static Friendship create(Member sender, Member receiver) {
        return Friendship.builder()
            .sender(sender)
            .receiver(receiver)
            .status(FriendshipStatus.PENDING)
            .build();
    }

    public Member getFriendOf(Member me) {
        return sender.equals(me) ? receiver : sender;
    }

    public void updateStatus(Member actor, FriendshipStatus newStatus) {
        if (isParticipant(actor)) {
            throw ChaeumException.from(ErrorCode.FORBIDDEN_FRIENDSHIP_ACCESS);
        }

        if (isSender(actor) && newStatus == FriendshipStatus.CANCELED) {
            this.status = newStatus;
            return;
        }

        if (isReceiver(actor) && EnumSet.of(ACCEPTED, REJECTED, BLOCKED).contains(newStatus)) {
            this.status = newStatus;
            return;
        }

        throw ChaeumException.from(ErrorCode.INVALID_FRIENDSHIP_STATUS_TRANSITION);
    }

    private boolean isSameMember(Member a, Member b) {
        return a != null && b != null && a.getId().equals(b.getId());
    }

    public boolean isParticipant(Member member) {
        return !isSender(member) && !isReceiver(member);
    }

    public boolean isSender(Member member) {
        return isSameMember(sender, member);
    }

    public boolean isReceiver(Member member) {
        return isSameMember(receiver, member);
    }
}
