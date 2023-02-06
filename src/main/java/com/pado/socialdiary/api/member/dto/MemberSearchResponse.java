package com.pado.socialdiary.api.member.dto;

import com.pado.socialdiary.api.common.constants.YesNoCode;
import lombok.Getter;
import lombok.Setter;

import static com.pado.socialdiary.api.common.constants.YesNoCode.N;

@Getter
@Setter
public class MemberSearchResponse {

    private Integer memberId;
    private String email;
    private String picture;

    private String name;
    private String nickname;

    private YesNoCode followAt = N;

    public MemberSearchResponse(Integer memberId, String email, String picture, String name, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.picture = picture;
        this.name = name;
        this.nickname = nickname;
    }
}
