package com.chaeum.api.domain.donation.service;

import com.chaeum.api.domain.donation.dto.response.DonationInteractionReward;
import com.chaeum.api.domain.donation.dto.response.DonationRewardResponse;
import com.chaeum.api.domain.inventory.service.InventoryService;
import com.chaeum.api.domain.item.entity.Item;
import com.chaeum.api.domain.item.entity.ItemCategory;
import com.chaeum.api.domain.item.entity.ItemGrade;
import com.chaeum.api.domain.item.service.ItemService;
import com.chaeum.api.domain.member.entity.Member;
import com.chaeum.api.global.auth.util.LoginMemberProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DonationRewardService {

    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final LoginMemberProvider loginMemberProvider;

    private static final List<String> INTERACTION_ITEMS = List.of("밥주기", "쓰다듬기", "놀아주기");

    @Transactional
    public DonationRewardResponse generateDonationReward() {
        Member member = loginMemberProvider.getCurrentLoginMember();

        // 상호작용 보상 결정: 목록에서 두 개의 아이템 선택 및 각 1~3개 랜덤 수량 지정
        List<DonationInteractionReward> interactionRewards = generateInteractionRewards();

        // 랜덤 포인트 보상 결정: 10 ~ 500 사이의 랜덤 포인트
        int pointReward = generateRandomPoint();

        // 비상호작용 보상 결정: 상호작용이 아닌 아이템 중 ItemGrade 확률로 등급 선택 후 해당 등급 아이템 랜덤 선택
        Long nonInteractionRewardItemId = generateNonInteractionReward(member);

        // 보상 획득
        inventoryService.saveRewardInventory(pointReward, interactionRewards, nonInteractionRewardItemId);

        return DonationRewardResponse.create(
            interactionRewards,
            pointReward,
            nonInteractionRewardItemId
        );
    }

    private int generateRandomPoint() {
        return ThreadLocalRandom.current().nextInt(10, 501);
    }

    private List<DonationInteractionReward> generateInteractionRewards() {
        // 상호작용 아이템 목록을 섞은 후, 앞의 두 개를 선택
        List<String> items = new ArrayList<>(INTERACTION_ITEMS);
        Collections.shuffle(items);
        List<DonationInteractionReward> rewards = new ArrayList<>();

        // 두 개의 아이템 각각 1~3개 보상
        for (int i = 0; i < 2; i++) {
            int quantity = ThreadLocalRandom.current().nextInt(1, 4);
            rewards.add(DonationInteractionReward.create(items.get(i), quantity));
        }

        return rewards;
    }

    private Long generateNonInteractionReward(Member member) {
        // ItemGrade의 확률에 따라 보상 등급 선택
        double totalWeight = calculateTotalWeight();
        double randomValue = ThreadLocalRandom.current().nextDouble() * totalWeight;

        // 랜덤한 값으로 아이템 등급 설정
        ItemGrade selectedGrade = getSelectedGrade(randomValue);

        // 만약 SelectedGrade가 null이라면 가장 낮은 등급 보상
        if (selectedGrade == null) {
            selectedGrade = ItemGrade.BRONZE;
        }

        // 상호작용이 아닌 카테고리(DECORATION, INTERIOR)에 해당하는 아이템 중에서,
        // 선택된 등급을 가진 아이템을 조회
        List<Item> candidateItems = itemService.findByCategoryInAndGrade(
            List.of(ItemCategory.DECORATION, ItemCategory.INTERIOR),
            selectedGrade
        );

        List<Item> filteredCandidates = candidateItems.stream()
            .filter(item -> !inventoryService.existsByMemberIdAndItemId(member.getId(), item.getId()))
            .toList();

        if (filteredCandidates.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(filteredCandidates.size());
        return filteredCandidates.get(randomIndex).getId();
    }

    private ItemGrade getSelectedGrade(double randomValue) {
        double cumulative = 0.0;
        ItemGrade selectedGrade = null;
        for (ItemGrade grade : ItemGrade.values()) {
            cumulative += grade.getProbability();
            if (randomValue <= cumulative) {
                selectedGrade = grade;
                break;
            }
        }
        return selectedGrade;
    }

    private double calculateTotalWeight() {
        double totalWeight = 0.0;
        for (ItemGrade grade : ItemGrade.values()) {
            totalWeight += grade.getProbability();
        }
        return totalWeight;
    }
}
