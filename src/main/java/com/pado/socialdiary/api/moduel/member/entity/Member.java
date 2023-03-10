package com.pado.socialdiary.api.moduel.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Member {

    private Integer memberId;
    private String email;

    @JsonIgnore
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
