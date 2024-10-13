package me.leeminsoo.usedpark.repository.user;

import me.leeminsoo.usedpark.domain.user.RefreshToken;
import me.leeminsoo.usedpark.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Query("SELECT r " +
            "FROM RefreshToken r " +
            "JOIN FETCH r.user " +
            "WHERE r.refreshToken = :refreshToken"
    )
    Optional<RefreshToken> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("SELECT r " +
            "FROM RefreshToken r " +
            "JOIN FETCH r.user " +
            "WHERE r.user = :user"
    )
    Optional<RefreshToken> findByUser(@Param("user") User user); // getPrincipal로 추출한 user는 영속화 x
}
