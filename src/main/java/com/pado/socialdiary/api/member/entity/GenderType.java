package com.pado.socialdiary.api.member.entity;

public enum GenderType {
    MAN, WOMAN, ETC;

    public static GenderType toGender(String paramGender) {

        if (paramGender.equals("M")) {
            return MAN;
        }

        return ETC;
    }
}
