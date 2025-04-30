-- [FUNDING 더미 데이터]
TRUNCATE TABLE funding RESTART IDENTITY CASCADE;

INSERT INTO funding (funding_id, title, content, goal_amount, current_amount, status,
                     end_date, member_id, funding_image, item_link,
                     is_reviewed, address, created_at, updated_at)

VALUES
    (1, '노후 냉장고 교체 지원', '일상 생활에 꼭 필요한 냉장고를 교체하고자 합니다.', 1499000, 119658, 'ONGOING',
     '2025-04-17', 1, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image1.jpg', 'https://www.coupang.com/vp/products/7983771101?itemId=22166949217&vendorItemId=89213419860&sourceType=srp_product_ads&clickEventId=23d4c4e0-25aa-11f0-9d5e-4d4e3cc9992c&korePlacement=15&koreSubPlacement=1&q=%EB%83%89%EC%9E%A5%EA%B3%A0&itemsCount=35&searchId=d330772b178221&rank=0&searchRank=0&isAddedCart=',
     false, '서울시 은평구', '2025-03-26', '2025-03-26'),

    (2, '초등생 체육복 지원', '체육 수업에 참여할 수 있도록 체육복을 준비하려 합니다.', 1878000, 350002, 'ONGOING',
     '2025-06-06', 2, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image2.jpg', 'https://www.coupang.com/vp/products/6424887173?itemId=15529265394&vendorItemId=87147855410&sourceType=srp_product_ads&clickEventId=44eb1350-25aa-11f0-bc79-0f996a5d7ce8&korePlacement=15&koreSubPlacement=5&q=%EC%B2%B4%EC%9C%A1%EB%B3%B5+%EC%B4%88%EB%93%B1%ED%95%99%EC%83%9D&itemsCount=36&searchId=79adaded186004&rank=4&searchRank=4&isAddedCart=',
     false, '경기도 수원시', '2025-03-18', '2025-03-18'),

    (3, '치매 노인을 위한 안전장비', '낙상을 방지하기 위한 안전 손잡이와 경보기를 설치하고자 합니다.', 800000, 395968, 'ONGOING',
     '2025-05-26', 3, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image3.jpg', 'https://www.coupang.com/vp/products/6226321843',
     false, '부산시 해운대구', '2025-03-20', '2025-03-20'),

    (4, '독거노인 쌀 지원', '혼자 사시는 어르신께 따뜻한 식사를 제공하고 싶습니다.', 1000000, 435457, 'ONGOING',
     '2025-06-09', 4, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image4.jpg', 'https://www.coupang.com/np/search?q=쌀+10kg',
     false, '대구시 달서구', '2025-03-15', '2025-03-15'),

    (5, '장애인 가정 식탁 교체', '앉을 수 있는 테이블 하나가 절실한 가정에 식탁을 드리고자 합니다.', 500000, 194944, 'ONGOING',
     '2025-05-30', 5, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image5.jpg', 'https://www.coupang.com/np/categories/184676',
     false, '광주시 북구', '2025-03-15', '2025-03-15'),

    (6, '유기동물 돌봄 사료 지원', '보호소에서 보호 중인 동물들에게 기본적인 식량을 지원합니다.', 800000, 394147, 'ONGOING',
     '2025-05-01', 6, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image6.jpg', 'https://www.coupang.com/np/search?q=강아지+사료+10kg',
     false, '충청남도 천안시', '2025-03-27', '2025-03-27'),

    (7, '중학생 온라인 수업 장비 후원', '기초생활수급 가정의 아이가 원활하게 수업을 들을 수 있게 합니다.', 800000, 273311, 'ONGOING',
     '2025-06-01', 7, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image7.jpg', 'https://www.coupang.com/vp/products/7032856940',
     false, '전라북도 군산시', '2025-03-21', '2025-03-21'),

    (8, '청년 취업용 노트북 후원', '취업을 준비하는 청년에게 노트북을 제공하려 합니다.', 500000, 71984, 'ONGOING',
     '2025-05-26', 8, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image8.jpg', 'https://www.coupang.com/np/categories/497135',
     false, '강원도 춘천시', '2025-03-03', '2025-03-03'),

    (9, '고령 장애인의 휠체어 지원', '움직임이 불편한 고령 장애인에게 적합한 휠체어를 구매합니다.', 800000, 122942, 'ONGOING',
     '2025-05-04', 9, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image9.jpg', 'https://www.coupang.com/np/categories/114699',
     false, '경상남도 창원시', '2025-03-11', '2025-03-11'),

    (10, '다문화가정 자녀 학용품 지원', '신학기를 맞아 준비물을 마련할 수 있도록 돕고자 합니다.', 800000, 283195, 'ONGOING',
     '2025-04-10', 10, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image10.jpg', 'https://www.coupang.com/np/search?q=학용품+세트',
     false, '제주특별자치도 제주시', '2025-03-05', '2025-03-05'),

    (11, '저소득 가정 연탄 후원', '난방비 부담이 큰 가정을 위해 따뜻한 겨울을 선물해주세요.', 300000, 180000, 'ONGOING',
     '2025-11-10', 14, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image11.jpg', 'https://www.coupang.com/vp/products/1876025214',
     false, '경기도 의정부시', '2025-04-04', '2025-04-04'),

    (13, '저소득 청년 면접 정장 구입', '면접을 앞둔 청년에게 첫 인상을 위한 정장을 준비하고자 합니다.', 250000, 100000, 'ONGOING',
     '2025-06-14', 12, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image13.jpg', 'https://www.coupang.com/np/search?q=면접+정장',
     false, '서울시 마포구', '2025-04-06', '2025-04-06'),

    (14, '에어컨 없는 가정의 시원한 여름나기', '폭염에 노출된 독거노인의 건강을 위해 에어컨을 설치하고 싶습니다.', 850000, 400000, 'ONGOING',
     '2025-07-20', 17, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image14.jpg', 'https://www.coupang.com/np/categories/227820',
     false, '광주시 광산구', '2025-04-08', '2025-04-08'),

    (16, '알레르기 환아 침구 교체', '집먼지진드기 알레르기로 고생하는 아이에게 기능성 침구를 선물해주세요.', 400000, 150000, 'ONGOING',
     '2025-07-01', 19, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image16.jpg', 'https://www.coupang.com/np/search?q=항알레르기+침구',
     false, '충청남도 공주시', '2025-04-10', '2025-04-10'),

    (17, '소외계층 자녀 책상 마련', '좁은 공간에서 바닥에서 공부하던 아이에게 책상과 의자를 마련해주세요.', 300000, 130000, 'ONGOING',
     '2025-06-20', 16, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image17.jpg', 'https://www.coupang.com/np/search?q=학생용+책상',
     false, '부산시 사하구', '2025-04-11', '2025-04-11'),

    (18, '수험생 수능 교재 후원', '대입을 앞둔 수험생에게 부족한 교재를 채워주세요.', 200000, 70000, 'ONGOING',
     '2025-07-25', 20, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image18.jpg', 'https://www.coupang.com/np/search?q=수능+교재+세트',
     false, '서울시 구로구', '2025-04-12', '2025-04-12'),

    (19, '몸이 불편한 부친의 휠체어 구매', '거동이 불편한 아버지를 위해 적합한 휠체어를 구입하고자 합니다.', 600000, 200000, 'ONGOING',
     '2025-07-10', 13, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image19.jpg', 'https://www.coupang.com/np/categories/114699',
     false, '전주시 완산구', '2025-04-13', '2025-04-13'),

    (20, '기초 생계 물품 꾸러미 전달', '쌀, 라면, 휴지 등 생필품을 꾸러미 형태로 나누고 싶습니다.', 250000, 50000, 'ONGOING',
     '2025-06-25', 11, 'https://bucket.s3.ap-northeast-2.amazonaws.com/funding/image20.jpg', 'https://www.coupang.com/np/search?q=생필품+세트',
     false, '경상북도 구미시', '2025-04-14', '2025-04-14');
