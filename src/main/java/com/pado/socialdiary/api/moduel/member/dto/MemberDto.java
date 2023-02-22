package com.pado.socialdiary.api.moduel.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class MemberDto implements Serializable {

    private Integer memberId;
    private String email;
    private String nickname;
    private String picture;
}
