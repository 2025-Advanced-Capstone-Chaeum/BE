package com.chaeum.api.domain.notification.event;

import com.chaeum.api.domain.donation.event.DonationEvent;
import com.chaeum.api.domain.inventory.event.RewardEvent;
import com.chaeum.api.domain.friendship.event.FriendEvent;
import com.chaeum.api.domain.funding.event.FundingEvent;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.domain.notification.entity.Notification;
import com.chaeum.api.domain.notification.entity.NotificationType;
import com.chaeum.api.domain.notification.repository.NotificationRepository;
import com.chaeum.api.domain.review.event.ReviewEvent;
import com.chaeum.api.domain.title.event.TitleEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListner {

    private final NotificationRepository notificationRepository;

    //TODO: 수혜자 등록 로직이 있을 때 주석을 해제하고 관련 이벤트 로직을 추가합니다.
//    @TransactionalEventListener
//    public void handleBeneficiary(BeneficiaryEvent e) {
//        notificationRepository.save(Notification.create(
//            e.getMemberId(), e.getTargetId(), e.getType(), e.getNotificationImageUrl(), e.getContent()
//        ));
//    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDonation(DonationEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.DONATION, e.getFundingImageUrl(), e.getContent()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleFriend(FriendEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.FRIEND, receiver.getProfileImage(), e.getContent()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleFunding(FundingEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.FUNDING, e.getFundingImageUrl(), e.getContent()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleReview(ReviewEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.REVIEW, e.getFundingImageUrl(), e.getContent()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleReward(RewardEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.REWARD, e.getItemImageUrl(), e.getContent()
        ));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleTitle(TitleEvent e) {
        Member receiver = e.getReceiver();
        notificationRepository.save(Notification.create(
            receiver.getId(), e.getId(), NotificationType.TITLE, null, e.getContent()
        ));
    }
}
