package com.pado.socialdiary.api.moduel.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

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

    @Parameter(name = "한줄소개")
    private String introduce;

    // TODO: 2023/03/11 마이바티스 이넘 이상하게 들어감
    @Parameter(name = "성별")
    private String gender;

    @Parameter(name = "생년", example = "2022")
    private String year;

    @Parameter(name = "생월", example = "5")
    private String month;

    @Parameter(name = "생일", example = "5")
    private String dayOfMonth;

    /**
     * 서비스에서 사용하는 필드
     */
    @JsonIgnore
    private LocalDateTime convertedDateOfBirth;

    @JsonIgnore
    private String encodedPassword;
}
