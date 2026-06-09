package kr.pindong.backend.config.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.pindong.backend.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PindongOAuth2User user = (PindongOAuth2User) authentication.getPrincipal();

        UUID userId = user.getUser().getId();
        String accessToken = jwtProvider.generateAccessToken(userId);
        String refreshToken = jwtProvider.generateRefreshToken();

        stringRedisTemplate.opsForValue().set(
                "refresh:" + userId,
                refreshToken,
                jwtProvider.getRefreshExpiration(),
                TimeUnit.MILLISECONDS
        );

        getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/oauth/callback?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
