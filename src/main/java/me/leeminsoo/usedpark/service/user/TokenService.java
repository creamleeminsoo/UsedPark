package me.leeminsoo.usedpark.service.user;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.InvalidInput.InvalidTokenException;
import me.leeminsoo.usedpark.config.jwt.TokenProvider;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new InvalidTokenException();
        }
        User user = refreshTokenService.findByRefreshToken(refreshToken).getUser();
        return tokenProvider.generateToken(user, Duration.ofHours(1));
    }
}
