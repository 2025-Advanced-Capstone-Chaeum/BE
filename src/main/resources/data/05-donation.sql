-- [DONATION 더미 데이터] (관리자 중심, 기부자는 1회 기부만)
TRUNCATE TABLE donation RESTART IDENTITY CASCADE;

INSERT INTO donation (donation_id, amount, created_at, updated_at, funding_id, member_id, status)
VALUES
-- 펀딩 1
(1, 20000, '2025-04-01 10:00:00', '2025-04-01 10:00:00', 1, 1, 'COMPLETED'),

-- 펀딩 2
(2, 19000, '2025-04-02 11:00:00', '2025-04-02 11:00:00', 2, 1, 'COMPLETED'),

-- 펀딩 3
(3, 30000, '2025-04-03 09:30:00', '2025-04-03 09:30:00', 3, 1, 'COMPLETED'),

-- 펀딩 4(기부 없음)

-- 펀딩 5 (기부자 2 참여)
(5, 15000, '2025-04-05 15:10:00', '2025-04-05 15:10:00', 5, 2, 'COMPLETED'),

-- 펀딩 6 (기부자 3 참여)
(6, 18000, '2025-04-06 16:00:00', '2025-04-06 16:00:00', 6, 3, 'COMPLETED');

-- 시퀀스 재설정
SELECT setval('donation_donation_id_seq', 5);
