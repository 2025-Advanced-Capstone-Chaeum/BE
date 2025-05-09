-- [NOTIFICATION 더미 데이터]
TRUNCATE TABLE notification RESTART IDENTITY CASCADE;

INSERT INTO notification (notification_id, member_id, target_id, notification_type, notification_image_url, content,
                          created_at, updated_at)
VALUES
-- 리뷰 알림
(1, 1, 2, 'REVIEW', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-review.png',
 '“할아버지를 위한 안전장비를 설치하고 싶어요.”의 후기가 등록되었어요.', '2025-04-02 10:30:00', '2025-04-02 10:30:00'),

-- 칭호 알림
(2, 2, 2, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "나눔의 씨앗"을 획득하셨어요!', '2025-04-22 15:10:00', '2025-04-22 15:10:00'),
(3, 3, 3, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "나눔의 씨앗"을 획득하셨어요!', '2025-04-29 16:00:00', '2025-04-29 16:00:00'),
(4, 1, 1, 'TITLE', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-title.png',
 '칭호 "희망의 새싹"을 획득하셨어요!', '2025-05-08 09:30:00', '2025-05-08 09:30:00'),

-- 기부 알림
(5, 1, 1, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "노후된 냉장고를 교체하고 싶어요."에 기부가 완료되었습니다.', '2025-04-01 10:05:00', '2025-04-01 10:05:00'),
(6, 1, 2, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "치매 노인을 위한 안전장비"에 기부가 완료되었습니다.', '2025-04-21 11:05:00', '2025-04-21 11:05:00'),
(7, 1, 3, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "호흡기 질환 예방 공기청정기 지원"에 기부가 완료되었습니다.', '2025-05-08 09:35:00', '2025-05-08 09:35:00'),
(8, 2, 5, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
    '펀딩 "휠체어가 있으면 할머니가 좋아하실 거 같아요.', '2025-04-22 15:10:00', '2025-04-22 15:10:00'),
(9, 3, 6, 'DONATION', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-donation.png',
 '펀딩 "다같이 먹을 식탁이 필요해요..', '2025-04-29 16:00:00', '2025-04-29 16:10:00'),

-- 펀딩 상태 알림
(10, 1, 1, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"노후된 냉장고를 교체하고 싶어요." 펀딩이 진행 중입니다.', '2025-03-26 09:00:00', '2025-03-26 09:00:00'),
(11, 1, 2, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"할아버지를 위한 안전장비를 설치하고 싶어요." 펀딩이 완료되었습니다.', '2025-04-30 10:00:00', '2025-04-30 10:00:00'),
(12, 1, 3, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"호흡기 질환 예방 공기청정기가 필요해요." 펀딩이 진행 중입니다.', '2025-03-29 09:00:00', '2025-03-29 09:00:00'),
(13, 1, 4, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '"대학 과제를 위해 노트북이 필요해요." 펀딩이 진행 중입니다.', '2025-03-03 09:00:00', '2025-03-03 09:00:00'),
(14, 4, 5, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '회원님의 펀딩 "휠체어가 있으면 할머니가 좋아하실 거 같아요."가 등록되었습니다.', '2025-04-10 09:00:00', '2025-04-10 09:00:00'),
(15, 5, 6, 'FUNDING', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-funding.png',
 '회원님의 펀딩 "다같이 먹을 식탁이 필요해요."가 등록되었습니다.', '2025-04-10 09:10:00', '2025-04-10 09:10:00'),

-- 미션 보상 알림
(16, 1, 1, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 1번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-01 09:15:00', '2025-04-01 09:15:00'),
(17, 2, 6, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 1번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-02 10:15:00', '2025-04-02 10:15:00'),
(18, 2, 8, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 6번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-02 10:15:00', '2025-04-02 10:15:00'),
(19, 3, 11, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 1번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-03 11:25:00', '2025-04-03 11:25:00'),
(20, 3, 13, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 6번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-03 11:25:00', '2025-04-03 11:25:00'),
(21, 5, 21, 'REWARD', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-mission.png',
 '미션 1번을 완료하셨어요! 포인트를 확인해보세요.', '2025-04-12 11:15:00', '2025-04-12 11:15:00'),

-- 친구 요청 알림
(22, 4, 2, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
 '김지훈님으로부터 친구 요청이 도착했어요.', '2025-04-08 11:00:00', '2025-04-08 11:00:00'),
(23, 2, 1, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
    '김채움님과 친구가 되었습니다.', '2025-04-01 09:10:00', '2025-04-01 09:10:00'),
(24, 3, 2, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
 '김채움님과 친구가 되었습니다.', '2025-04-01 09:10:00', '2025-04-01 09:10:00'),
(25, 4, 3, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
 '김채움님과 친구가 되었습니다.', '2025-04-01 09:10:00', '2025-04-01 09:10:00'),
(26, 5, 4, 'FRIEND', 'https://s3.ap-northeast-2.amazonaws.com/item/notification-friend.png',
 '김채움님과 친구가 되었습니다.', '2025-04-01 09:10:00', '2025-04-01 09:10:00');

-- 시퀀스 재설정
SELECT setval('notification_notification_id_seq', 26);
