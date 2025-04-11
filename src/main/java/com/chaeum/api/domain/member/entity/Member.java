package com.chaeum.api.domain.member.entity;

import com.chaeum.api.domain.cat.entity.Cat;
import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.member.dto.request.MemberUpdateRequest;
import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import com.chaeum.api.domain.title.entity.Title;
import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "is_beneficiary", nullable = false)
    private Boolean isBeneficiary = Boolean.FALSE;

    @Column(name = "profile_image")
    private String profileImage;

    @Builder.Default
    @Column(name = "points", nullable = false, precision = 10, scale = 2)
    private BigDecimal points = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_login_type")
    private SocialLoginType socialLoginType;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cat cat;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventoryItems = List.of();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentRecord> payments = List.of();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = List.of();

    @Builder.Default
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> sentFriendships = List.of();

    @Builder.Default
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> receivedFriendships = List.of();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles = List.of();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MissionProgress> missionProgresses;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Notification> notifications;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews;

    public void update(MemberUpdateRequest memberUpdateRequest) {
        Optional.ofNullable(memberUpdateRequest.getName()).ifPresent(this::setName);
        Optional.ofNullable(memberUpdateRequest.getProfileImage()).ifPresent(this::setProfileImage);
    }

    public void deductPoints(BigDecimal point) {
        this.points = this.points.subtract(point);
    }

    public void addPoints(BigDecimal point) {
        validatePointAmount(point);
        this.points = this.points.add(point);
    }

    public void validatePointInsufficient(BigDecimal point) {
        if (this.points.compareTo(point) < 0) {
            throw ChaeumException.from(ErrorCode.INSUFFICIENT_POINTS);
        }
    }

    private void validatePointAmount(BigDecimal point) {
        if (point.compareTo(BigDecimal.ZERO) < 0) {
            throw ChaeumException.from(ErrorCode.INVALID_POINT_AMOUNT);
        }
    }

    public boolean isSame(Member other) {
        return other != null && this.id != null && this.id.equals(other.getId());
    }
}
