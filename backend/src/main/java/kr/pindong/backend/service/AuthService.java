package kr.pindong.backend.service;

import kr.pindong.backend.config.jwt.JwtProvider;
import kr.pindong.backend.dto.request.RefreshRequest;
import kr.pindong.backend.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtProvider jwtProvider;

    public String refresh(RefreshRequest request) {
        String refreshToken = stringRedisTemplate.opsForValue().get("refresh:" + request.userId());
        if (refreshToken == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        if (!refreshToken.equals(request.refreshToken())) {
            stringRedisTemplate.delete("refresh:" + request.userId());
            throw new UnauthorizedException("Invalid refresh token");
        }

        return jwtProvider.generateAccessToken(request.userId());
    }

    public void logout(UUID userId) {
        stringRedisTemplate.delete("refresh:" + userId);
    }
}
