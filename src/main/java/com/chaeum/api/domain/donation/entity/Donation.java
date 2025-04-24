package com.chaeum.api.domain.donation.entity;

import com.chaeum.api.domain.donation.dto.request.DonationCreateRequest;
import com.chaeum.api.domain.funding.entity.Funding;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "donation")
public class Donation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DonationStatus status;

    public static Donation toEntity(DonationCreateRequest request, Member member, Funding funding) {
        BigDecimal finalAmount = getFinalAmount(request);
        return Donation.builder()
            .member(member)
            .funding(funding)
            .amount(finalAmount)
            .status(DonationStatus.ONGOING)
            .build();
    }

    public void manageStatus(DonationStatus status) {
        this.status = status;
    }

    private static BigDecimal getFinalAmount(DonationCreateRequest request) {
        return request.getAmount().add(request.getPoint());
    }
}
