package com.pado.socialdiary.api.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class AuthorizationDto {

    @Getter
    @Setter
    public static class TokenResponse {
        private String grantType = "Bearer";
        private String accessToken;

        @Builder
        public TokenResponse(String grantType, String accessToken) {
            this.grantType = grantType;
            this.accessToken = accessToken;
        }
    }
}
