-- [UPLOADED_FILE 더미 데이터]
TRUNCATE TABLE uploaded_file RESTART IDENTITY CASCADE;

INSERT INTO uploaded_file (uploaded_file_id, file_url, original_file_name, stored_file_name, file_size, content_type,
                           review_id, funding_id, created_at, updated_at)
VALUES
-- 펀딩 이미지 (funding_id: 1~6)
(1, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-1.png',
 'funding-1.png', 'uuid-funding-1.png', 95000, 'image/png',
 NULL, 1, '2025-03-26 10:00:00', '2025-03-26 10:00:00'),

(2, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-2.png',
 'funding-2.png', 'uuid-funding-2.png', 97000, 'image/png',
 NULL, 2, '2025-03-20 11:00:00', '2025-03-20 11:00:00'),

(3, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-3.png',
 'funding-3.png', 'uuid-funding-3.png', 91000, 'image/png',
 NULL, 3, '2025-03-29 09:00:00', '2025-03-29 09:00:00'),

(4, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-4.png',
 'funding-4.png', 'uuid-funding-4.png', 100200, 'image/png',
 NULL, 4, '2025-03-03 14:00:00', '2025-03-03 14:00:00'),

(5, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-5.png',
 'funding-5.png', 'uuid-funding-5.png', 88000, 'image/png',
 NULL, 5, '2025-04-10 15:00:00', '2025-04-10 15:00:00'),

(6, 'https://s3.ap-northeast-2.amazonaws.com/item/funding-6.png',
 'funding-6.png', 'uuid-funding-6.png', 89000, 'image/png',
 NULL, 6, '2025-04-10 15:10:00', '2025-04-10 15:10:00'),

-- 리뷰 이미지 (review_id: 1, funding_id: 2)
(7, 'https://s3.ap-northeast-2.amazonaws.com/item/review-2.png',
 'review-2.png', 'uuid-review-2.png', 101320, 'image/png',
 1, 2, '2025-04-02 10:00:00', '2025-04-02 10:00:00');

-- 시퀀스 재설정
SELECT setval('uploaded_file_uploaded_file_id_seq', 7);
