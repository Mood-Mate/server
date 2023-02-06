package com.pado.socialdiary.api.common.constants;

public enum RefTable implements CommonCode {
    HP_MEMBER("HP_MEMBER", "회원 테이블"),
    TB_DIARY("TB_DIARY", "다이어리 테이블");

    private final String value;
    private final String desc;

    RefTable(String value, String desc) {
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
