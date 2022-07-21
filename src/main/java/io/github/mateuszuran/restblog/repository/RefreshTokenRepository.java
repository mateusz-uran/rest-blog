package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.RefreshToken;
import io.github.mateuszuran.restblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
