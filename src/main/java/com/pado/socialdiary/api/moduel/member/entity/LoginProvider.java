package com.pado.socialdiary.api.moduel.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.print.attribute.standard.MediaSize;

public enum LoginProvider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;

    LoginProvider(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
