package com.pado.socialdiary.api.common.constants;

public enum AttachPath implements CommonCode {
    MEMBER_PICTURE("member/picture/", "회원 프로필");

    private final String value;
    private final String desc;

    AttachPath(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
