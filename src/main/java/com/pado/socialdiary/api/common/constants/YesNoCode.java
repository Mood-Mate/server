package com.pado.socialdiary.api.common.constants;

public enum YesNoCode implements CommonCode {

    Y("Y","예"),
    N("N","아니오");

    private final String value;
    private final String desc;

    YesNoCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
