package com.toyproj.pinchhitterhomerun.type;

public enum ErrorMessage {
    SUCCESS("성공"),

    HINT_NOT_EXIST("존재하지 않는 힌트입니다."),

    MEMBER_DB_ERROR("멤버 DB 에러"),
    MEMBER_ID_ALREADY_USED("이미 사용중인 아이디입니다."),
    MEMBER_LOGIN_FAILED("아이디 혹은 비밀번호가 잘못 되었습니다."),
    MEMBER_NOT_EXIST("존재하지 않는 회원입니다."),
    MEMBER_WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    MEMBER_BRANCH_NOT_EXIST("해당 회원이 속한 지점이 없습니다."),
    MEMBER_BRANCH_ALREADY_EXIST("이미 속한 지점이 있습니다."),
    MEMBER_HINT_DB_ERROR("멤버 힌트 DB 에러"),
    PASSWORD_HINT_NOT_MATCHED("틀린 답변입니다."),
    PASSWORD_HINT_NOT_FOUND("비밀번호 힌트를 찾을 수 없습니다."),
    
    HINT_DB_ERROR("힌트 DB 에러"),

    CATEGORY_NOT_EXIST("존재하지 않는 카테고리입니다."),
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다."),

    BRAND_NOT_EXIST("존재하지 않는 브랜드입니다."),
    BRAND_NOT_FOUND("브랜드를 찾을 수 없습니다."),

    BRANCH_NOT_EXIST("존재하지 않는 지점입니다."),
    BRANCH_NOT_FOUND("지점을 찾을 수 없습니다."),
    BRANCH_EMPTY_EMPLOYEE("알바생이 없습니다."),

    REQUEST_DB_ERROR("요청 DB 에러"),
    REQUEST_NOT_EXIST("존재하지 않는 지점 요청입니다."),
    REQUEST_NOT_FOUND("지점 요청을 찾을 수 없습니다."),
    REQUEST_ALREADY_HAVE_BRANCH("다른 지점에 속해있습니다."),
    REQUEST_ALREADY_REQUESTED("이미 요청하였습니다."),
    REQUEST_ALREADY_EXPIRED("이미 처리된 지점 요청입니다."),

    BOARD_NOT_FOUND("찾으려는 결과가 없습니다."),
    BOARD_DB_ERROR("게시판 DB 에러"),
    BOARD_MEMBER_HAVE_NOT_BRANCH("사용자가 속한 지점이 없습니다."),
    BOARD_INVALID_DATE_TIME("유효하지 않는 시간 설정입니다."),
    BOARD_HAVE_TO_24_HOURS_BEFORE("24시간 이전의 대타만 모집할 수 있습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
