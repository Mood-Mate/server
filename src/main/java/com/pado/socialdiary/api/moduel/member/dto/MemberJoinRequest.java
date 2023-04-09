package com.pado.socialdiary.api.moduel.member.dto;

import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinRequest {

    @Parameter(name = "이메일", example = "email@email.com")
    @Email
    private String email;

    @Parameter(name = "비밀번호", example = "1234")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#?])[a-zA-Z\\d!@#?]{6,20}$")
    private String password;

    @Parameter(name = "비밀번호 확인", example = "1234")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#?])[a-zA-Z\\d!@#?]{6,20}$")
    private String passwordConfirm;

    @Parameter(name = "이름", example = "이름")
    private String name;

    @Parameter(name = "닉네임", example = "닉네임")
    private String nickname;

    @Parameter(name = "성별", example = "ETC")
    private GenderType gender;

    @Parameter(name = "생년", example = "2022")
    private Integer year;

    @Parameter(name = "생월", example = "5")
    private Integer month;

    @Parameter(name = "생일", example = "5")
    private Integer dayOfMonth;
}
