-- [TITLE 더미 데이터]
TRUNCATE TABLE title RESTART IDENTITY CASCADE;

INSERT INTO title (title_id, title_name, member_id, created_at, updated_at)
VALUES
-- 관리자 (기부 3회) → SPROUT
(1, 'SPROUT', 1, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부자 1 (김지훈, 기부 1회) → SAESSAK
(2, 'SAESSAK', 2, '2025-04-22 12:00:00', '2025-04-22 12:00:00'),

-- 기부자 2 (이미진, 기부 1회) → SAESSAK
(3, 'SAESSAK', 3, '2025-04-22 12:00:00', '2025-04-22 12:00:00');

-- 시퀀스 재설정
SELECT setval('title_title_id_seq', 3);
