-- [FUNDING 더미 데이터]
-- 관리자 4건, 수혜자 각 1건

TRUNCATE TABLE funding RESTART IDENTITY CASCADE;

INSERT INTO funding (funding_id, title, content, goal_amount, current_amount, status,
                     end_date, member_id, item_link, is_reviewed, address, created_at, updated_at)
VALUES
-- 관리자가 만든 펀딩 (member_id = 1)
(1, '노후된 냉장고를 교체하고 싶어요.', '기존 노후된 냉장고가 냉장 기능이 잘 안되는 거 같아요. 노후된 냉장고를 교체하고자 합니다.',
 374650, 20000, 'ONGOING', '2025-07-17', 1,
 'https://www.coupang.com/vp/products/8338421081',
 false, '서울시 은평구', '2025-03-26', '2025-03-26'),

(2, '할아버지를 위한 안전장비를 설치하고 싶어요.', '치매를 겪는 할머니를 위해 낙상을 방지하기 위한 안전 손잡이와 경보기를 설치하고자 합니다.',
 19000, 19000, 'COMPLETED', '2025-04-30', 1,
 'https://www.coupang.com/vp/products/8510774985',
 true, '부산시 해운대구', '2025-03-30', '2025-03-30'),

(3, '호흡기 질환 예방 공기청정기가 필요해요.', '미세먼지와 알레르기로 잔기침을 많이해요. 가정에 공기청정기를 두려고 합니다.',
 238460, 30000, 'ONGOING', '2025-07-10', 1,
 'https://www.coupang.com/vp/products/1771619336',
 false, '경기도 성남시', '2025-03-29', '2025-03-29'),

(4, '대학 과제를 위해 노트북이 필요해요.', '대학 과제에 노트북이 꼭 필요해요. 부탁드려요.',
 449000, 0, 'ONGOING', '2025-05-26', 1,
 'https://www.coupang.com/vp/products/8365032869',
 false, '강원도 춘천시', '2025-03-03', '2025-03-03'),

-- 수혜자1이 만든 펀딩 (member_id = 4)
(5, '휠체어가 있으면 할머니가 좋아하실 거 같아요.', '제가 다리가 아파서 혼자 외출이 어렵더라구요. 휠체어가 있으면 정말 편할 거 같습니다.',
 168000, 15000, 'ONGOING', '2025-06-19', 4,
 'https://www.coupang.com/vp/products/7337849360',
 false, '서울시 종로구', '2025-04-10', '2025-04-10'),

-- 수혜자2가 만든 펀딩 (member_id = 5)
(6, '다같이 먹을 식탁이 필요해요.', '저희가 인원이 많은데 식탁이 너무 작더라구요. 함께 앉아 식사할 수 있는 추가적인 식탁이 필요해요.',
 209000, 18000, 'ONGOING', '2025-07-01', 5,
 'https://www.coupang.com/vp/products/8017270860',
 false, '제주특별자치도 제주시', '2025-04-10', '2025-04-10');

-- 시퀀스 재설정
SELECT setval('funding_funding_id_seq', 6);
