-- [PAYMENT 더미 데이터]
TRUNCATE TABLE payment RESTART IDENTITY CASCADE;

INSERT INTO payment (payment_id, member_id, amount, payment_method, status,
                     imp_uid, merchant_uid, pg_provider, fail_reason,
                     created_at, updated_at)
VALUES
-- 관리자 김채움 (member_id: 1)
(1, 1, 20000, 'KAKAO_PAY', 'COMPLETED', 'imp_202504010001', 'merchant_202504010001', 'nice', NULL,
 '2025-04-01 09:55:00', '2025-04-01 09:55:00'),
(2, 1, 19000, 'KAKAO_PAY', 'COMPLETED', 'imp_202504020001', 'merchant_202504020001', 'nice', NULL,
 '2025-04-02 10:50:00', '2025-04-02 10:50:00'),
(3, 1, 30000, 'KAKAO_PAY', 'COMPLETED', 'imp_202504030001', 'merchant_202504030001', 'nice', NULL,
 '2025-04-03 09:25:00', '2025-04-03 09:25:00'),

-- 기부자 김지훈 (member_id: 2)
(4, 2, 15000, 'TOSS_PAY', 'COMPLETED', 'imp_202504051001', 'merchant_202504051001', 'toss', NULL,
 '2025-04-05 15:05:00', '2025-04-05 15:05:00'),

-- 기부자 이미진 (member_id: 3)
(5, 3, 18000, 'PAYCO', 'COMPLETED', 'imp_202504061001', 'merchant_202504061001', 'kcp', NULL,
 '2025-04-06 15:55:00', '2025-04-06 15:55:00');

-- 시퀀스 재설정
SELECT setval('payment_record_payment_record_id_seq', 5);
