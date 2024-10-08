package me.leeminsoo.usedpark.repository.user;

import me.leeminsoo.usedpark.domain.user.RefreshToken;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUser(User user);
}
