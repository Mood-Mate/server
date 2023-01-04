package com.pado.socialdiary.api.member.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Member {

    // TODO: 2023/01/04 [ERROR] Principal Response, Member pk is null;
    private Integer memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime dateOfBirth;
    private GenderType gender;

    private String role = "USER";

    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public Member(String email, String password, String name, String nickname, LocalDateTime dateOfBirth, GenderType gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;

        this.regDt = LocalDateTime.now();

        this.updDt = LocalDateTime.now();
    }
}
