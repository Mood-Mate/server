package com.pado.socialdiary.api.member.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Member {

    private Integer memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime dateOfBirth;
    private GenderType gender;

    private MemberRole role = MemberRole.USER;
    private LoginProvider loginProvider;

    private String regId;
    private LocalDateTime regDt;
    private String updId;
    private LocalDateTime updDt;

    @Builder
    public Member(Integer memberId, String email, String password, String name, String nickname, LocalDateTime dateOfBirth, GenderType gender, LoginProvider loginProvider) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;

        this.loginProvider = loginProvider;

        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }
}
