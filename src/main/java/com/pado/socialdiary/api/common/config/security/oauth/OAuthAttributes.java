package com.pado.socialdiary.api.common.config.security.oauth;

import com.pado.socialdiary.api.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;

    private String email;
    private String name;
    private String nameAttributeKey;
//    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if (registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes);
        } else if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        } else {
            return null;
        }
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes) {

        Map kakao_account = (Map) attributes.get("kakao_account");
        Map properties = (Map) attributes.get("properties");

        String email = (String) kakao_account.get("email");
        String nickname = (String) properties.get("nickname");

        System.out.println(email);

        return OAuthAttributes.builder()
                .email(email)
                .name(nickname)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Map<String, Object> toMap() {
        System.out.println(attributes);
        System.out.println(nameAttributeKey);

        HashMap<String, Object> result = new HashMap<>();
        result.put("email", this.email);
        result.put("name", this.email);

        return result;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password("")
                .name(name)
                .build();
    }
}
