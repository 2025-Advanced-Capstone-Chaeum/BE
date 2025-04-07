package com.chaeum.api.domain.member.entity;

import com.chaeum.api.domain.cat.entity.Cat;
import com.chaeum.api.domain.donation.entity.Donation;
import com.chaeum.api.domain.friendship.entity.Friendship;
import com.chaeum.api.domain.inventory.entity.Inventory;
import com.chaeum.api.domain.member.dto.request.MemberUpdateRequest;
import com.chaeum.api.domain.paymentRecord.entity.PaymentRecord;
import com.chaeum.api.domain.title.entity.Title;
import com.chaeum.api.global.entity.BaseEntity;
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

    @Column(name = "points")
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_login_type")
    private SocialLoginType socialLoginType;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cat cat;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventoryItems;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentRecord> payments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> friendships;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MissionProgress> missionProgresses;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Attendance> attendances;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Notification> notifications;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews;

    public void update(MemberUpdateRequest memberUpdateRequest) {
        Optional.ofNullable(memberUpdateRequest.getName()).ifPresent(this::setName);
        Optional.ofNullable(memberUpdateRequest.getProfileImage()).ifPresent(this::setProfileImage);
    }
}
