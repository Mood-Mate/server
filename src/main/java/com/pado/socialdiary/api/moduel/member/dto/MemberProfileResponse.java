package com.pado.socialdiary.api.moduel.member.dto;

import com.pado.socialdiary.api.constants.ResourcePath;
import com.pado.socialdiary.api.moduel.member.entity.LoginProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileResponse {

    private Integer memberId;

    private LoginProvider loginProvider;

    private String name;
    private String nickname;

    private String picture;

    private Integer followerCount;
    private Integer followeeCount;

    private String introduce;

    public MemberProfileResponse(Integer memberId, LoginProvider loginProvider, String name, String nickname, String picture, String introduce) {
        this.memberId = memberId;
        this.loginProvider = loginProvider;
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
        this.introduce = introduce;
    }
}
