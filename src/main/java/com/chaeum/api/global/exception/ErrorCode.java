package com.chaeum.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // Common Errors
    REQUEST_OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 작업 중 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 호출 중 오류가 발생했습니다."),

    // Member Errors
    MEMBER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "사용자가 인증되지 않았습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_MEMBER_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용 중인 로그인 ID입니다."),
    DUPLICATE_MEMBER_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    DUPLICATE_MEMBER_PHONE_NUMBER(HttpStatus.CONFLICT, "이미 사용 중인 전화번호입니다."),
    PROFILE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필 이미지를 찾을 수 없습니다."),
    MEMBER_NOT_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다."),

    // Authentication & Token Errors
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    EXPIRED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 인증 토큰입니다."),
    NOT_BEARER_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "Bearer 타입의 토큰이 아닙니다."),
    NEED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    OAUTH2_PROVIDER_NOT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth2 제공자가 응답하지 않습니다."),
    OPEN_ID_PROVIDER_NOT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "OpenID 제공자가 응답하지 않습니다."),

    // Transaction errors
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "거래를 찾을 수 없습니다."),
    TRANSACTION_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 거래입니다."),
    TRANSACTION_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "해당 거래에 대한 권한이 없습니다."),
    TRANSACTION_EXPIRED(HttpStatus.BAD_REQUEST, "거래 기간이 만료되었습니다."),
    TRANSACTION_INVALID_STATUS(HttpStatus.BAD_REQUEST, "잘못된 거래 상태입니다."),
    TRANSACTION_SHIPPING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "배송 처리에 실패했습니다."),
    TRANSACTION_CANCEL_NOT_ALLOWED(HttpStatus.FORBIDDEN, "거래를 취소할 수 없습니다."),
    TRANSACTION_REFUND_NOT_ALLOWED(HttpStatus.FORBIDDEN, "환불을 처리할 수 없습니다."),
    TRANSACTION_ITEM_NOT_RECEIVED(HttpStatus.BAD_REQUEST, "상품이 수령되지 않았습니다."),
    TRANSACTION_ITEM_DAMAGED(HttpStatus.BAD_REQUEST, "상품이 손상되었습니다."),
    TRANSACTION_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "잘못된 수량입니다."),
    TRANSACTION_DUPLICATE_REQUEST(HttpStatus.CONFLICT, "중복된 거래 요청입니다."),
    TRANSACTION_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 주소를 찾을 수 없습니다."),
    TRANSACTION_INSUFFICIENT_FUNDS(HttpStatus.PAYMENT_REQUIRED, "잔액이 부족합니다."),

    // Item Errors
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    ITEM_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 상품입니다."),
    ITEM_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "해당 상품은 거래할 수 없습니다."),
    ITEM_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "상품이 품절되었습니다."),
    ITEM_DISCONTINUED(HttpStatus.BAD_REQUEST, "단종된 상품입니다."),

    // Review Errors
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),

    // File Upload Errors
    EMPTY_IMAGE(HttpStatus.BAD_REQUEST, "이미지 파일이 비어있습니다."),
    UNSUPPORTED_IMAGE_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 이미지 파일 형식입니다."),
    IMAGE_STORE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지를 저장하는 데 실패했습니다."),
    IMAGE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "이미지 파일 크기가 제한을 초과했습니다."),
    S3_UPLOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 파일 업로드 중 오류가 발생했습니다."),

    // Payment Errors
    PAY_FAILURE(HttpStatus.BAD_REQUEST, "결제에 실패했습니다."),
    PAY_INVALID(HttpStatus.BAD_REQUEST, "잘못된 결제 정보입니다."),
    PAY_PRICE_MISMATCH(HttpStatus.BAD_REQUEST, "결제 금액이 상품 금액과 일치하지 않습니다."),
    PAY_NOT_FOUND(HttpStatus.BAD_REQUEST, "결제 내역을 찾을 수 없습니다."),

    // ETC Errors
    NOT_ENOUGH_PERMISSION(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    INTERNAL_SERVER_ERROR_GENERIC(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다. 관리자에게 문의하세요.");

    private final HttpStatus status;
    private final String message;
}