-- [MEMBER 더미 데이터]
-- 관리자 1명, 기부자 9명, 수혜자 10명 (총 20명)

TRUNCATE TABLE member RESTART IDENTITY CASCADE;

INSERT INTO member (email, name, profile_image, role, social_login_type, is_beneficiary, points, created_at, updated_at)
VALUES
-- 관리자
    ('admin@chaeum.com', '김채움', NULL, 'ADMIN', 'NAVER', FALSE, 0, '2025-04-01 09:00:00', '2025-04-01 09:00:00'),

-- 기부자 (2~10)
    ('jihun.kim@example.com', '김지훈', NULL, 'DONOR', 'NAVER', FALSE, 15000, '2025-04-02 10:00:00', '2025-04-02 10:00:00'),
    ('minji.lee@example.com', '이미진', NULL, 'DONOR', 'KAKAO', FALSE, 26000, '2025-04-03 11:10:00', '2025-04-03 11:10:00'),
    ('youngho.choi@example.com', '최영호', NULL, 'DONOR', 'NAVER', FALSE, 9800, '2025-04-04 14:00:00', '2025-04-04 14:00:00'),
    ('sumin.park@example.com', '박수민', NULL, 'DONOR', 'KAKAO', FALSE, 34000, '2025-04-05 15:20:00', '2025-04-05 15:20:00'),
    ('doyeon.jung@example.com', '정도연', NULL, 'DONOR', 'NAVER', FALSE, 5000, '2025-04-06 12:30:00', '2025-04-06 12:30:00'),
    ('seokwoo.kang@example.com', '강석우', NULL, 'DONOR', 'NAVER', FALSE, 42000, '2025-04-07 13:10:00', '2025-04-07 13:10:00'),
    ('yewon.han@example.com', '한예원', NULL, 'DONOR', 'KAKAO', FALSE, 11000, '2025-04-08 08:30:00', '2025-04-08 08:30:00'),
    ('sihyun.lee@example.com', '이시현', NULL, 'DONOR', 'NAVER', FALSE, 22000, '2025-04-09 17:45:00', '2025-04-09 17:45:00'),
    ('hyojin.kim@example.com', '김효진', NULL, 'DONOR', 'KAKAO', FALSE, 37000, '2025-04-10 10:10:00', '2025-04-10 10:10:00'),

-- 수혜자 (11~20)
    ('seoyeon.park@example.com', '박서연', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-11 13:45:00', '2025-04-11 13:45:00'),
    ('jinwoo.kim@example.com', '김진우', NULL, 'RECIPIENT', 'KAKAO', TRUE, 0, '2025-04-12 11:00:00', '2025-04-12 11:00:00'),
    ('haeun.lee@example.com', '이하은', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-13 09:00:00', '2025-04-13 09:00:00'),
    ('siwoo.cho@example.com', '조시우', NULL, 'RECIPIENT', 'KAKAO', TRUE, 0, '2025-04-14 10:55:00', '2025-04-14 10:55:00'),
    ('nayeon.choi@example.com', '최나연', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-15 08:33:00', '2025-04-15 08:33:00'),
    ('hyejin.song@example.com', '송혜진', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-16 19:10:00', '2025-04-16 19:10:00'),
    ('yoonho.bae@example.com', '배윤호', NULL, 'RECIPIENT', 'KAKAO', TRUE, 0, '2025-04-17 07:00:00', '2025-04-17 07:00:00'),
    ('jungwoo.lim@example.com', '임정우', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-18 10:10:00', '2025-04-18 10:10:00'),
    ('yuna.jeong@example.com', '정유나', NULL, 'RECIPIENT', 'KAKAO', TRUE, 0, '2025-04-19 15:15:00', '2025-04-19 15:15:00'),
    ('daehyun.hwang@example.com', '황대현', NULL, 'RECIPIENT', 'NAVER', TRUE, 0, '2025-04-20 18:40:00', '2025-04-20 18:40:00');
