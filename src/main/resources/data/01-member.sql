-- [MEMBER 더미 데이터]
-- 관리자 1명, 기부자 2명, 수혜자 2명 (총 5명)

TRUNCATE TABLE member RESTART IDENTITY CASCADE;

INSERT INTO member (member_id, email, name, profile_image, role, social_login_type, is_beneficiary, points, created_at, updated_at)
VALUES
-- 관리자
(1, 'admin@chaeum.com', '김채움', NULL, 'ADMIN', 'NAVER', FALSE, 0, '2025-04-01 09:00:00', '2025-04-01 09:00:00'),

-- 기부자 (2~3)
(2, 'jihun.kim@example.com', '김지훈', NULL, 'DONOR', 'NAVER', FALSE, 15000, '2025-04-02 10:00:00', '2025-04-02 10:00:00'),
(3, 'minji.lee@example.com', '이미진', NULL, 'KAKAO', 'DONOR', FALSE, 26000, '2025-04-03 11:10:00', '2025-04-03 11:10:00'),

-- 수혜자 (4~5)
(4, 'seoyeon.park@example.com', '박서연', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-11 13:45:00', '2025-04-11 13:45:00'),
(5, 'jinwoo.kim@example.com', '김진우', NULL, 'RECIPIENT', 'KAKAO', TRUE, 0, '2025-04-12 11:00:00', '2025-04-12 11:00:00');

-- 시퀀스 재설정
SELECT setval('member_member_id_seq', 5);
