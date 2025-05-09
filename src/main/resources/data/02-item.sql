-- [ITEM 더미 데이터]
-- 상호작용 3종, 장식 3종, 인테리어 3종 (총 9개)

TRUNCATE TABLE item RESTART IDENTITY CASCADE;

-- 상호작용 (BRONZE)
INSERT INTO item (item_id, name, category, grade, item_image_url, created_at, updated_at)
VALUES (1, '밥주기', 'INTERACTION', 'BRONZE', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/feed.png',
        '2025-04-01 09:00:00', '2025-04-01 09:00:00'),
       (2, '쓰다듬기', 'INTERACTION', 'BRONZE', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/pet.png',
        '2025-04-01 09:00:00', '2025-04-01 09:00:00'),
       (3, '놀아주기', 'INTERACTION', 'BRONZE', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/play.png',
        '2025-04-01 09:00:00', '2025-04-01 09:00:00'),

-- 장식
       (4, '안경', 'DECORATION', 'BRONZE', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/glasses.png',
        '2025-04-02 10:00:00', '2025-04-02 10:00:00'),
       (5, '모자', 'DECORATION', 'SILVER', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/hat.png',
        '2025-04-02 10:10:00', '2025-04-02 10:10:00'),
       (6, '목걸이', 'DECORATION', 'PLATINUM', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/necklace.png',
        '2025-04-02 10:20:00', '2025-04-02 10:20:00'),

-- 인테리어
       (7, '침대', 'INTERIOR', 'GOLD', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/bed.png',
        '2025-04-03 11:00:00', '2025-04-03 11:00:00'),
       (8, '나무 모형', 'INTERIOR', 'SILVER', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/tree.png',
        '2025-04-03 11:10:00', '2025-04-03 11:10:00'),
       (9, '캣타워', 'INTERIOR', 'DIAMOND', 'https://bucket.s3.ap-northeast-2.amazonaws.com/item/cattower.png',
        '2025-04-03 11:20:00', '2025-04-03 11:20:00');

-- 시퀀스 재설정
SELECT setval('item_item_id_seq', 9);
