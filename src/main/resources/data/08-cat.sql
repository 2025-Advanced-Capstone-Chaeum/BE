-- [CAT 더미 데이터]
TRUNCATE TABLE cat RESTART IDENTITY CASCADE;

INSERT INTO cat (cat_id, member_id, experience_point, level, created_at, updated_at)
VALUES
-- 관리자 (member_id = 1)
(1, 1, 50, 1, '2025-04-01 09:30:00', '2025-04-29 08:00:00'),

-- 기부자 1 (member_id = 2)
(2, 2, 80, 2, '2025-04-02 10:00:00', '2025-04-29 08:10:00'),

-- 기부자 2 (member_id = 3)
(3, 3, 150, 3, '2025-04-03 11:10:00', '2025-04-29 08:20:00'),

-- 수혜자 1 (member_id = 4)
(4, 4, 120, 3, '2025-04-11 13:45:00', '2025-04-29 09:40:00'),

-- 수혜자 2 (member_id = 5)
(5, 5, 30, 1, '2025-04-12 11:00:00', '2025-04-29 09:50:00');

-- 시퀀스 재설정
SELECT setval('cat_cat_id_seq', 5);
