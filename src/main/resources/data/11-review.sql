-- [REVIEW 더미 데이터] (펀딩 종료일 이후 리뷰 등록)

TRUNCATE TABLE review RESTART IDENTITY CASCADE;

INSERT INTO review (review_id, funding_id, title, content, created_at, updated_at)
VALUES
    (1, 2, '할아버지를 위한 안전장비를 설치하고 싶어요.',
     '낙상 걱정이 많았던 할아버지가 안전하게 지내실 수 있게 되었어요. 정말 감사합니다.',
     '2025-04-02 10:00:00', '2025-04-02 10:00:00');

-- 시퀀스 재설정
SELECT setval('review_review_id_seq', 1);
