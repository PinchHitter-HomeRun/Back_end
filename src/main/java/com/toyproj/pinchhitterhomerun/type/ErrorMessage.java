package com.toyproj.pinchhitterhomerun.type;

public enum ErrorMessage {
    MEMBER_ID_ALREADY_USED("이미 사용중인 아이디입니다."),
    MEMBER_LOGIN_FAILED("아이디 혹은 비밀번호가 잘못 되었습니다."),
    MEMBER_NOT_EXIST("존재하지 않는 회원입니다."),
    MEMBER_BRANCH_NOT_EXIST("해당 회원이 속한 지점이 없습니다."),
    CATEGORY_NOT_EXIST("존재하지 않는 카테고리입니다."),
    BRAND_NOT_EXIST("존재하지 않는 브랜드입니다."),
    BRANCH_NOT_EXIST("존재하지 않는 지점입니다."),
    BRANCH_NOT_FOUND("지점을 찾을 수 없습니다."),
    REQUEST_NOT_EXIST("존재하지 않는 요청입니다."),
    REQUEST_NOT_FOUND("요청을 찾을 수 없습니다."),
    REQUEST_ALREADY_EXIST("이미 요청하였습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
