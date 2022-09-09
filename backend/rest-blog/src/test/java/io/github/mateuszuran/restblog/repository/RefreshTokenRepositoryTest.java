package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RefreshTokenRepositoryTest {
    @Autowired
    private RefreshTokenRepository repository;

    @Test
    void givenRefreshTokenObject_whenFindByRefreshToken_thenReturnObject() {
        //given
        RefreshToken token = new RefreshToken("ABCDEFGH", Instant.now());
        repository.save(token);
        //when
        var result = repository.findByRefreshToken(token.getRefreshToken()).orElseThrow();
        //then
        assertThat(result).isEqualTo(token);
    }
}