package com.chaeum.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400: BAD REQUEST (잘못된 요청)
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNSUPPORTED_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기가 제한을 초과했습니다."),
    UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth2 제공자입니다."),
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 소셜 로그인 타입입니다."),
    INVENTORY_QUANTITY_INSUFFICIENT(HttpStatus.BAD_REQUEST, "재고 수량이 0 이하로 감소할 수 없습니다."),
    ITEM_CATEGORY_MISMATCH(HttpStatus.BAD_REQUEST, "아이템 카테고리가 올바르지 않습니다."),
    INVALID_PAYMENT_METHOD(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 수단입니다."),
    INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 상태입니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "결제 금액이 일치하지 않습니다."),
    PAYMENT_VERIFY_FAILED(HttpStatus.BAD_REQUEST, "결제 검증에 실패했습니다."),
    DONATION_AMOUNT_EXCEEDS_GOAL(HttpStatus.BAD_REQUEST, "기부 금액은 목표 금액을 넘을 수 없습니다."),
    INSUFFICIENT_POINTS(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
    INVALID_POINT_AMOUNT(HttpStatus.BAD_REQUEST, "포인트는 음수가 될 수 없습니다."),
    INVALID_FRIENDSHIP_STATUS(HttpStatus.BAD_REQUEST, "존재하지 않거나 잘못된 친구 상태입니다."),
    INVALID_SELF_FRIENDSHIP(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 요청을 보낼 수 없습니다."),
    INVALID_FRIENDSHIP_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "현재 친구 상태에서는 해당 변경이 불가능합니다."),
    INVALID_FRIENDSHIP_ACTION(HttpStatus.BAD_REQUEST, "현재 친구 상태에서는 이 요청을 처리할 수 없습니다."),
    GOAL_AMOUNT_NOT_REACHED(HttpStatus.BAD_REQUEST, "목표 기부 금액을 넘어서지 못했습니다."),
    FUNDING_IS_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "펀딩이 종료되지 않았습니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다."),
    ALREADY_ATTENDED_TODAY(HttpStatus.BAD_REQUEST, "이미 오늘 출석을 완료했습니다."),
    ALREADY_HAS_TITLE(HttpStatus.BAD_REQUEST, "이미 보유한 칭호입니다."),

    // 401: UNAUTHORIZED (인증 실패)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    MEMBER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "사용자가 인증되지 않았습니다."),
    NEED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 인증 토큰입니다."),
    EXPIRED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    NOT_BEARER_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "Bearer 타입의 토큰이 아닙니다."),

    // 403: FORBIDDEN (권한 없음)
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    MEMBER_NOT_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다."),
    NOT_ENOUGH_PERMISSION(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    FORBIDDEN_FRIENDSHIP_ACCESS(HttpStatus.FORBIDDEN, "해당 친구 요청에 접근할 수 있는 권한이 없습니다."),

    // 404: NOT FOUND (리소스를 찾을 수 없음)
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    PROFILE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필 이미지를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."),
    INVENTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "인벤토리를 찾을 수 없습니다."),
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND, "펀딩 정보를 찾을 수 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 정보를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    NOT_FOUND_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "엑세스 토큰을 찾을 수 없습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."),
    CAT_NOT_FOUND(HttpStatus.NOT_FOUND, "고양이를 찾을 수 없습니다."),
    DONATION_NOT_FOUND(HttpStatus.NOT_FOUND, "기부 내역을 찾을 수 없습니다."),
    FRIENDSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 관계를 찾을 수 없습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),
    INCLUDE_NOT_UPLOADED_FILE(HttpStatus.NOT_FOUND, "서버에 업로드되지 않은 파일이 포함되어 있습니다."),
    ATTENDANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "출석 정보를 찾을 수 없습니다."),
    TITLE_NOT_FOUND(HttpStatus.NOT_FOUND, "칭호를 찾을 수 없습니다."),

    // 409: CONFLICT (중복된 요청)
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    ALREADY_FRIENDSHIP_EXISTS(HttpStatus.CONFLICT, "이미 친구 관계가 존재합니다."),

    // 500: INTERNAL SERVER ERROR (서버 내부 오류)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 작업 중 오류가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 호출 중 오류가 발생했습니다."),
    OAUTH2_PROVIDER_NOT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth2 제공자가 응답하지 않습니다."),
    OPEN_ID_PROVIDER_NOT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "OpenID 제공자가 응답하지 않습니다."),
    IMAGE_STORE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지를 저장하는 데 실패했습니다."),
    S3_UPLOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 파일 업로드 중 오류가 발생했습니다."),
    PAYMENT_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 정보를 저장에 실패했습니다."),
    INTERNAL_SERVER_ERROR_GENERIC(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다. 관리자에게 문의하세요.");

    private final HttpStatus status;
    private final String message;
}
