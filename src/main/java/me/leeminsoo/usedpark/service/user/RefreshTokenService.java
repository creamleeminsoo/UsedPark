package me.leeminsoo.usedpark.service.user;

import lombok.RequiredArgsConstructor;
import me.leeminsoo.usedpark.config.error.exception.notpound.RefreshTokenNotFoundException;
import me.leeminsoo.usedpark.domain.user.RefreshToken;
import me.leeminsoo.usedpark.repository.user.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
    }
}
