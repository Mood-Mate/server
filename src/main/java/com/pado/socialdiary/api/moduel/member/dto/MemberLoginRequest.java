package com.pado.socialdiary.api.moduel.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberLoginRequest {
    private String email;
    private String password;
}
