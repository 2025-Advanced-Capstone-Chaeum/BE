-- [NOTIFICATION 더미 데이터]
TRUNCATE TABLE notification RESTART IDENTITY CASCADE;

INSERT INTO notification (notification_id, member_id, target_id, notification_type, notification_image_url, content,
                          created_at, updated_at)
VALUES
-- 리뷰 알림
(1, 1, 2, 'REVIEW', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-review.png',
 '“치매 노인을 위한 안전장비” 후원이 완료되어 후기가 등록되었어요.', '2025-04-02 10:30:00', '2025-04-02 10:30:00'),

-- 칭호 알림
(2, 1, 1, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "희망의 새싹"을 획득하셨어요!', '2025-04-03 09:00:00', '2025-04-03 09:00:00'),
(3, 2, 2, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "나눔의 씨앗"을 획득하셨어요!', '2025-04-03 09:10:00', '2025-04-03 09:10:00'),
(4, 3, 3, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "나눔의 씨앗"을 획득하셨어요!', '2025-04-03 09:20:00', '2025-04-03 09:20:00'),

-- 기부 알림
(5, 1, 1, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "노후 냉장고 교체 지원"에 기부가 완료되었습니다.', '2025-04-01 10:05:00', '2025-04-01 10:05:00'),
(6, 2, 5, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "어르신 이동을 위한 휠체어 지원"에 기부가 완료되었습니다.', '2025-04-05 15:15:00', '2025-04-05 15:15:00'),
(7, 3, 6, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "다문화가정 식탁 지원"에 기부가 완료되었습니다.', '2025-04-06 16:10:00', '2025-04-06 16:10:00'),

-- 펀딩 상태 알림
(8, 1, 1, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"노후 냉장고 교체 지원" 펀딩이 진행 중입니다.', '2025-04-01 09:00:00', '2025-04-01 09:00:00'),
(9, 1, 2, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"치매 노인을 위한 안전장비" 펀딩이 완료되었습니다.', '2025-04-02 09:00:00', '2025-04-02 09:00:00'),
(10, 1, 3, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"호흡기 질환 예방 공기청정기 지원" 펀딩이 진행 중입니다.', '2025-04-03 09:00:00', '2025-04-03 09:00:00'),
(11, 1, 4, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"청년 취업용 노트북 후원" 펀딩이 진행 중입니다.', '2025-04-04 09:00:00', '2025-04-04 09:00:00'),
(12, 4, 5, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '회원님의 펀딩 "어르신 이동을 위한 휠체어 지원"이 등록되었습니다.', '2025-04-05 09:00:00', '2025-04-05 09:00:00'),
(13, 5, 6, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '회원님의 펀딩 "다문화가정 식탁 지원"이 등록되었습니다.', '2025-04-06 09:00:00', '2025-04-06 09:00:00'),

-- 미션 보상 알림
(14, 1, 101, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-reward.png',
 '출석 체크 미션 완료로 10포인트가 적립되었습니다.', '2025-04-07 07:00:00', '2025-04-07 07:00:00'),
(15, 2, 102, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-reward.png',
 '기부 미션을 달성하여 20포인트를 획득했어요!', '2025-04-07 07:10:00', '2025-04-07 07:10:00'),
(16, 3, 103, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-reward.png',
 '출석 체크 미션 완료로 10포인트가 적립되었습니다.', '2025-04-07 07:20:00', '2025-04-07 07:20:00'),

-- 친구 요청 알림
(17, 4, 2, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
 '김지훈님으로부터 친구 요청이 도착했어요.', '2025-04-08 11:00:00', '2025-04-08 11:00:00');

-- 시퀀스 재설정
SELECT setval('notification_notification_id_seq', 17);
