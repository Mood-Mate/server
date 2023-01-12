package com.pado.socialdiary.api.member.dto;

import com.pado.socialdiary.api.member.entity.GenderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;

    private GenderType gender;

    private Integer year;
    private Integer month;
    private Integer dayOfMonth;
}
