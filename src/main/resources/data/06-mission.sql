-- [MISSION 더미 데이터]
TRUNCATE TABLE mission RESTART IDENTITY CASCADE;

-- 출석 미션
INSERT INTO mission (mission_id, name, description, mission_image_url, mission_type, created_at, updated_at)
VALUES (1, '출석 체크하기', '오늘도 빠짐없이 출석 체크하고 채움을 기록해보세요!',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-attendance.png', 'ATTENDANCE',
        '2025-04-01 00:00:00', '2025-04-01 00:00:00'),

-- 고양이 경험치 미션
       (2, '고양이 경험치 올리기', '고양이와 교감하며 경험치를 쌓아보세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-cat-exp.png', 'CAT_EXP',
        '2025-04-02 00:00:00', '2025-04-02 00:00:00'),

-- 고양이 상호작용 미션
       (3, '고양이에게 밥 주기', '배고픈 고양이에게 맛있는 밥을 챙겨주세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-feed.png', 'CAT_INTERACTION',
        '2025-04-03 00:00:00', '2025-04-03 00:00:00'),

       (4, '고양이 쓰다듬기', '고양이를 다정하게 쓰다듬으며 친밀도를 높여보세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-pet.png', 'CAT_INTERACTION',
        '2025-04-04 00:00:00', '2025-04-04 00:00:00'),

       (5, '고양이와 놀아주기', '장난감을 활용해 고양이와 재미있는 시간을 보내세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-play.png', 'CAT_INTERACTION',
        '2025-04-05 00:00:00', '2025-04-05 00:00:00'),

-- 기부 미션
       (6, '기부 실천하기', '작은 정성이 누군가에게 큰 도움이 될 수 있어요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-donation.png', 'DONATION',
        '2025-04-06 00:00:00', '2025-04-06 00:00:00'),

-- 아이템 착용 미션
       (7, '장식 아이템 착용', '고양이에게 예쁜 장식 아이템을 착용해보세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-decoration.png', 'ITEM_WEAR',
        '2025-04-07 00:00:00', '2025-04-07 00:00:00'),

       (8, '인테리어 아이템 착용', '고양이 주변을 따뜻한 인테리어 아이템으로 꾸며보세요.',
        'https://bucket.s3.ap-northeast-2.amazonaws.com/item/mission-interior.png', 'ITEM_WEAR',
        '2025-04-08 00:00:00', '2025-04-08 00:00:00');

-- 시퀀스 재설정
SELECT setval('mission_mission_id_seq', 8);
