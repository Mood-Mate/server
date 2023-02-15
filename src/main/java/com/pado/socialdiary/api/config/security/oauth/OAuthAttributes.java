package com.pado.socialdiary.api.config.security.oauth;

import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.LoginProvider;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.pado.socialdiary.api.moduel.member.entity.LoginProvider.*;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;

    private LoginProvider loginProvider;

    private String email;
    private String name;
    private String nickname;
    private GenderType gender;
    private LocalDateTime dateOfBirth;
    private String picture;

    private String nameAttributeKey;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, LoginProvider loginProvider, String nickname, GenderType gender, LocalDateTime dateOfBirth, String picture) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.picture = picture;

        this.loginProvider = loginProvider;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthAttributes of(String oAuthProvider, String userNameAttributeName, Map<String, Object> attributes) {

        if (oAuthProvider.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes);
        } else if (oAuthProvider.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        } else {
            return ofNaver(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        System.out.println(attributes);

        int birthYear = Integer.parseInt(response.get("birthyear").toString());

        int birthMonth = Integer.parseInt(response.get("birthday").toString().substring(0, 2));
        int birthDayOfMonth = Integer.parseInt(response.get("birthday").toString().substring(3, 5));

        return OAuthAttributes.builder()
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .nickname((String) response.get("nickname"))
                .gender(GenderType.toGender((String) response.get("gender")))
                .dateOfBirth(LocalDateTime.of(birthYear, birthMonth, birthDayOfMonth, 0, 0))
                .loginProvider(NAVER)
                .picture((String) response.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        System.out.println(attributes);

        String memberFullName = (String) attributes.get("given_name") + " " + (String) attributes.get("family_name");

        return OAuthAttributes.builder()
                .email((String) attributes.get("email"))
                .name(memberFullName)
                .nickname((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .loginProvider(GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes) {

        Map kakaoAccount = (Map) attributes.get("kakao_account");
        Map kakaoProfile = (Map) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) kakaoProfile.get("nickname");
        String picture = (String) kakaoProfile.get("profile_image_url");

        // TODO: 2023/01/16 카카오 유저 프로퍼티 제공 권한 때문에 name == nickname
        return OAuthAttributes.builder()
                .email(email)
                .name(nickname)
                .nickname(nickname)
                .picture(picture)
                .loginProvider(KAKAO)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("email", this.email);
        result.put("name", this.email);

        return result;
    }

    public Member toEntity() {
        Member builtMember = Member.builder()
                .email(this.email)
                .password("")
                .name(this.name)
                .nickname(this.nickname)
                .dateOfBirth(this.dateOfBirth)
                .loginProvider(this.loginProvider)
                .gender(this.gender)
                .picture(this.picture)
                .build();

        System.out.println(builtMember);

        return builtMember;
    }
}
