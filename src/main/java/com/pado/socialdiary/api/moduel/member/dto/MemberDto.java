package com.pado.socialdiary.api.moduel.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pado.socialdiary.api.constants.ResourcePath;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.LoginProvider;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.entity.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class MemberDto implements Serializable {

    @Getter
    public static class Response implements Serializable{
        private Integer memberId;
        private String email;
        private String name;
        private String nickname;
        private LocalDateTime dateOfBirth;
        private GenderType gender;
        private String introduce;
        private String picture;

        @JsonIgnore
        private MemberRole role;

        @JsonIgnore
        private LoginProvider loginProvider;

        public Response(Member member) {
            this.memberId = member.getMemberId();
            this.email = member.getEmail();
            ;
            this.name = member.getName();
            this.nickname = member.getNickname();
            this.gender = member.getGender();
            this.introduce = member.getIntroduce();

            this.role = member.getRole();

            this.loginProvider = member.getLoginProvider();

            this.picture = member.getPicture();
            if (member.getLoginProvider() == LoginProvider.LOCAL) {
                this.picture = ResourcePath.MEMBER_PICTURE.getResource(member.getPicture());
            }

            this.dateOfBirth = member.getDateOfBirth();
        }
    }
}
