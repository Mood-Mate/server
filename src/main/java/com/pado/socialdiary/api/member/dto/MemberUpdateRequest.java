package com.pado.socialdiary.api.member.dto;

import com.pado.socialdiary.api.member.entity.GenderType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class MemberUpdateRequest {

    // TODO: 2023/01/17 운영 나가면 코드수정 필요
    @Parameter(name = "회원번호 Ex", example = "1")
    private Integer memberId;

    @Parameter(name = "비밀번호", example = "1234")
    private String password;

    @Parameter(name = "이름", example = "이름")
    private String name;

    @Parameter(name = "닉네임", example = "닉네임")
    private String nickname;

    @Parameter(name = "프로필사진 링크", example = "")
    private String picture;
}
