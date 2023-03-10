package com.pado.socialdiary.api.moduel.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public enum GenderType implements Serializable {
    MAN("MAN"),
    WOMAN("WOMAN"),
    ETC("ETC");

    private final String value;

    public static GenderType toGender(String paramGender) {

        if (paramGender.equals("M")) {
            return MAN;
        }

        return ETC;
    }
}
