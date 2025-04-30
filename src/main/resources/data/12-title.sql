-- [TITLE 더미 데이터]
TRUNCATE TABLE title RESTART IDENTITY CASCADE;

INSERT INTO title (title_id, title_name, member_id, created_at, updated_at)
VALUES
-- 기부 횟수: 5회 (member_id 2) → 희망의 새싹 (SPROUT)
(1, 'SPROUT', 2, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 1회 (member_id 3) → 나눔의 씨앗 (SAESSAK)
(2, 'SAESSAK', 3, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 2회 (member_id 4) → 나눔의 씨앗 (SAESSAK)
(3, 'SAESSAK', 4, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 3회 (member_id 5) → 나눔의 씨앗 (SAESSAK)
(4, 'SAESSAK', 5, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 4회 (member_id 6) → 나눔의 씨앗 (SAESSAK)
(5, 'SAESSAK', 6, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 3회 (member_id 7) → 나눔의 씨앗 (SAESSAK)
(6, 'SAESSAK', 7, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 1회 (member_id 8) → 나눔의 씨앗 (SAESSAK)
(7, 'SAESSAK', 8, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 2회 (member_id 9) → 나눔의 씨앗 (SAESSAK)
(8, 'SAESSAK', 9, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부 횟수: 3회 (member_id 10) → 나눔의 씨앗 (SAESSAK)
(9, 'SAESSAK', 10, '2025-04-22 12:00:00', '2025-04-22 12:00:00');
