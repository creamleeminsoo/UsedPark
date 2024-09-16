package me.leeminsoo.usedpark.repository.user;

import me.leeminsoo.usedpark.domain.user.User;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long count();
    Page<User> findByEmailContaining(String keyword, Pageable pageable);
    Page<User> findByNicknameContaining(String keyword, Pageable pageable);
}
