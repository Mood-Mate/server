package com.pado.socialdiary.api.moduel.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Member {

    private Integer memberId;
    @Email
    private String email;

    @JsonIgnore
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#?])[a-zA-Z\\d!@#?]{6,20}$")
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime dateOfBirth;
    private GenderType gender;
    private String introduce;
    private String picture;

    private MemberRole role = MemberRole.USER;

    @JsonIgnore
    private LoginProvider loginProvider = LoginProvider.LOCAL;

    @JsonIgnore
    private Integer regId;

    @JsonIgnore
    private LocalDateTime regDt;

    @JsonIgnore
    private Integer updId;

    @JsonIgnore
    private LocalDateTime updDt;

    @Builder
    public Member(Integer memberId, LoginProvider loginProvider, String email, String password, String name, String nickname, LocalDateTime dateOfBirth, GenderType gender, String picture) {
        this.memberId = memberId;
        this.loginProvider = loginProvider;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.picture = picture;
    }
}
