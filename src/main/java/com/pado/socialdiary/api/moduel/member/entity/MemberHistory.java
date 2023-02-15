package com.pado.socialdiary.api.moduel.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MemberHistory {

    private Integer memberHistoryId;
    private Integer memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDateTime dateOfBirth;
    private GenderType gender;
    private String picture;

    private MemberRole role = MemberRole.USER;
    private LoginProvider loginProvider = LoginProvider.LOCAL;

    private Integer regId;
    private LocalDateTime regDt;
    private Integer updId;
    private LocalDateTime updDt;

    public MemberHistory(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.dateOfBirth = member.getDateOfBirth();
        this.gender = member.getGender();
        this.picture = member.getPicture();
        this.role = member.getRole();
        this.loginProvider = member.getLoginProvider();
        this.regId = member.getRegId();
        this.regDt = member.getRegDt();
        this.updId = member.getMemberId();
    }
}
