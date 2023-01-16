package com.pado.socialdiary.api.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuthController {

    @GetMapping("/oauth")
    public String oauthForm() {
        return "OAuth2Test";
    }
}
