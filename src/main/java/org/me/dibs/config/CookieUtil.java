package org.me.dibs.config;

import org.me.dibs.constants.CookieConstant;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public ResponseCookie createJwtCookie(String token) {
        return ResponseCookie.from(CookieConstant.COOKIE_NAME.getValue(), token)
                .httpOnly(true)
                .secure(true)
                .sameSite(CookieConstant.SAME_SITE.getValue())
                .path(CookieConstant.PATH.getValue())
                .maxAge(CookieConstant.MAX_AGE.getLongValue())
                .build();
    }
}
