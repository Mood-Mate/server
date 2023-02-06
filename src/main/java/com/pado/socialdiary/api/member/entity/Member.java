package com.pado.socialdiary.api.member.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Member {

    private Integer memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime dateOfBirth;
    private GenderType gender;
    private String introduce;
    private String picture;

    private MemberRole role = MemberRole.USER;
    private LoginProvider loginProvider = LoginProvider.LOCAL;

    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    @Builder
    public Member(Integer memberId, String email, String password, String name, String nickname, LocalDateTime dateOfBirth, GenderType gender, LoginProvider loginProvider, String picture) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.picture = picture;
        this.loginProvider = loginProvider;
    }

    public void changeIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", picture='" + picture + '\'' +
                ", role=" + role +
                ", loginProvider=" + loginProvider +
                '}';
    }
}
