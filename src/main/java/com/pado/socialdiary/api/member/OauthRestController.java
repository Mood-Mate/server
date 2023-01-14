package com.pado.socialdiary.api.member;

import com.pado.socialdiary.api.common.config.security.oauth.Oauth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthRestController {

    private final Oauth2UserServiceImpl oauth2UserService;

//    https://kauth.kakao.com/oauth/authorize?client_id=b7e4a310abc54328e29fd9a6a2ff066c&redirect_uri=http://localhost:8080/api/oauth/kakao&response_type=code
//    b7e4a310abc54328e29fd9a6a2ff066c

    /**
     * 카카오 콜백 API
     * @return
     */
    @GetMapping("/form")
    public String callbackKakao() {
        return "OAuth2Test";
    }

    @ResponseBody
    @GetMapping("/kakao")
    public Object redirectKakao(String code) throws MalformedURLException, URISyntaxException {
        return oauth2UserService.ofKakao(code);
    }
}
