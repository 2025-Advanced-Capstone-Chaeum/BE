package com.chaeum.api.domain.cat.entity;

import com.chaeum.api.domain.cat.strategy.LevelUpStrategy;
import com.chaeum.api.domain.cat.strategy.PowerLevelUpStrategy;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.utils.LevelUpConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "cat")
public class Cat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "experience_point", nullable = false)
    private BigInteger experiencePoint;

    public static Cat toEntity(Member member) {
        return Cat.builder()
                .member(member)
                .level(1)
                .experiencePoint(BigInteger.ZERO)
                .build();
    }

    @Transient
    private static LevelUpStrategy levelUpStrategy =
            new PowerLevelUpStrategy(LevelUpConstants.BASE_EXP, LevelUpConstants.EXPONENT);

    public List<Integer> addExpAndGetLevelUps(BigInteger gainedExp) {
        this.experiencePoint = this.experiencePoint.add(gainedExp);
        return checkLevelUp();
    }

    public List<Integer> checkLevelUp() {
        List<Integer> leveledUpTo = new ArrayList<>();
        while (true) {
            BigInteger requiredExp = getRequiredExpForNextLevel();
            if (this.experiencePoint.compareTo(requiredExp) >= 0) {
                this.experiencePoint = this.experiencePoint.subtract(requiredExp);
                this.level++;
                leveledUpTo.add(this.level);
            } else {
                break;
            }
        }
        return leveledUpTo;
    }

    private BigInteger getRequiredExpForNextLevel() {
        return levelUpStrategy.getRequiredExp(this.level);
    }

    public double getLevelUpPercentage() {
        BigInteger requiredExp = getRequiredExpForNextLevel();
        if (requiredExp.equals(BigInteger.ZERO)) {
            return 0.0;
        }
        return experiencePoint.doubleValue() / requiredExp.doubleValue() * 100.0;
    }
}
