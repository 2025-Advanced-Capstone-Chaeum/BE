-- [FUNDING 더미 데이터]
-- 관리자 4건, 수혜자 각 1건

TRUNCATE TABLE funding RESTART IDENTITY CASCADE;

INSERT INTO funding (funding_id, title, content, goal_amount, current_amount, status,
                     end_date, member_id, item_link, is_reviewed, address, created_at, updated_at)
VALUES
-- 관리자가 만든 펀딩 (member_id = 1)
(1, '노후 냉장고 교체 지원', '일상 생활에 꼭 필요한 냉장고를 교체하고자 합니다.',
 374650, 20000, 'ONGOING', '2025-07-17', 1,
 'https://www.coupang.com/vp/products/8338421081',
 false, '서울시 은평구', '2025-03-26', '2025-03-26'),

(2, '치매 노인을 위한 안전장비', '낙상을 방지하기 위한 안전 손잡이와 경보기를 설치하고자 합니다.',
 19000, 19000, 'COMPLETED', '2025-04-01', 1,
 'https://www.coupang.com/vp/products/8510774985',
 true, '부산시 해운대구', '2025-03-20', '2025-03-20'),

(3, '호흡기 질환 예방 공기청정기 지원', '미세먼지와 알레르기로 힘들어하는 어르신 가정에 공기청정기를 지원합니다.',
 238460, 30000, 'ONGOING', '2025-07-10', 1,
 'https://www.coupang.com/vp/products/1771619336',
 false, '경기도 성남시', '2025-03-29', '2025-03-29'),

(4, '청년 취업용 노트북 후원', '취업을 준비하는 청년에게 노트북을 제공하려 합니다.',
 449000, 0, 'ONGOING', '2025-05-26', 1,
 'https://www.coupang.com/vp/products/8365032869',
 false, '강원도 춘천시', '2025-03-03', '2025-03-03'),

-- 수혜자1이 만든 펀딩 (member_id = 4)
(5, '어르신 이동을 위한 휠체어 지원', '혼자 외출이 어려운 어르신께 가벼운 접이식 휠체어를 지원합니다.',
 168000, 15000, 'ONGOING', '2025-06-19', 4,
 'https://www.coupang.com/vp/products/7337849360',
 false, '서울시 종로구', '2025-04-10', '2025-04-10'),

-- 수혜자2가 만든 펀딩 (member_id = 5)
(6, '다문화가정 식탁 지원', '함께 앉아 식사할 수 있는 식탁이 필요한 가정을 위한 지원입니다.',
 209000, 18000, 'ONGOING', '2025-07-01', 5,
 'https://www.coupang.com/vp/products/8017270860',
 false, '제주특별자치도 제주시', '2025-04-10', '2025-04-10');

-- 시퀀스 재설정
SELECT setval('funding_funding_id_seq', 6);
