-- [CAT 더미 데이터]
TRUNCATE TABLE cat RESTART IDENTITY CASCADE;

INSERT INTO cat (cat_id, member_id, name, image_url, exp, level, created_at, updated_at)
VALUES
-- 관리자 (member_id = 1)
(1, 1, '채움이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/1.png', 50, 1, '2025-04-01 09:30:00',
 '2025-04-29 08:00:00'),

-- 기부자 (member_id = 2~10)
(2, 2, '나비', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/2.png', 80, 2, '2025-04-02 10:00:00',
 '2025-04-29 08:10:00'),
(3, 3, '두부', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/3.png', 150, 3, '2025-04-03 11:10:00',
 '2025-04-29 08:20:00'),
(4, 4, '미소', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/4.png', 210, 4, '2025-04-04 14:00:00',
 '2025-04-29 08:30:00'),
(5, 5, '코코', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/5.png', 45, 1, '2025-04-05 15:20:00',
 '2025-04-29 08:40:00'),
(6, 6, '사랑이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/6.png', 300, 6, '2025-04-06 12:30:00',
 '2025-04-29 08:50:00'),
(7, 7, '마루', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/7.png', 25, 1, '2025-04-07 13:10:00',
 '2025-04-29 09:00:00'),
(8, 8, '단비', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/8.png', 140, 3, '2025-04-08 08:30:00',
 '2025-04-29 09:10:00'),
(9, 9, '초롱이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/9.png', 180, 4, '2025-04-09 17:45:00',
 '2025-04-29 09:20:00'),
(10, 10, '해솔', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/10.png', 220, 5, '2025-04-10 10:10:00',
 '2025-04-29 09:30:00'),

-- 수혜자 (member_id = 11~20)
(11, 11, '콩이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/11.png', 120, 3, '2025-04-11 13:45:00',
 '2025-04-29 09:40:00'),
(12, 12, '루비', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/12.png', 30, 1, '2025-04-12 11:00:00',
 '2025-04-29 09:50:00'),
(13, 13, '모카', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/13.png', 240, 5, '2025-04-13 09:00:00',
 '2025-04-29 10:00:00'),
(14, 14, '보리', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/14.png', 150, 3, '2025-04-14 10:55:00',
 '2025-04-29 10:10:00'),
(15, 15, '망고', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/15.png', 75, 2, '2025-04-15 08:33:00',
 '2025-04-29 10:20:00'),
(16, 16, '초코', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/16.png', 310, 6, '2025-04-16 19:10:00',
 '2025-04-29 10:30:00'),
(17, 17, '탄이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/17.png', 90, 2, '2025-04-17 07:00:00',
 '2025-04-29 10:40:00'),
(18, 18, '해피', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/18.png', 190, 4, '2025-04-18 10:10:00',
 '2025-04-29 10:50:00'),
(19, 19, '솜이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/19.png', 400, 7, '2025-04-19 15:15:00',
 '2025-04-29 11:00:00'),
(20, 20, '냥이', 'https://bucket.s3.ap-northeast-2.amazonaws.com/cat/20.png', 60, 1, '2025-04-20 18:40:00',
 '2025-04-29 11:10:00');
