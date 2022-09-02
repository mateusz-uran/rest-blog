package io.github.mateuszuran.restblog.security.services;

import io.github.mateuszuran.restblog.exception.TokenRefreshException;
import io.github.mateuszuran.restblog.model.RefreshToken;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.repository.RefreshTokenRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @InjectMocks
    RefreshTokenService service;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    UserRepository userRepository;
    User user;
    RefreshToken token;

    @Test
    void givenTokenObject_whenFindByToken_thenReturnObject() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar");
        userRepository.save(user);
        token = new RefreshToken(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(6000)
        );
        refreshTokenRepository.save(token);
        given(refreshTokenRepository.findByRefreshToken(token.toString())).willReturn(Optional.of(token));
        //when
        var result = service.findByToken(token.toString()).orElseThrow();
        //then
        assertThat(result).isEqualTo(token);
    }

    @Test
    void givenUserObject_whenCreateToken_thenVerify() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar");
        userRepository.save(user);
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        /*token = new RefreshToken(user, UUID.randomUUID().toString(), Instant.now().plusMillis(60000L));*/
        ReflectionTestUtils.setField(service, "refreshTokenDurationMs", 6000L);
        //when
        service.createRefreshToken(user.getId());
        //then
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void givenUserAndToken_whenVerifyExpiration_thenReturnException() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar");
        userRepository.save(user);
        token = new RefreshToken(user, UUID.randomUUID().toString(), Instant.now().plusMillis(0));
        refreshTokenRepository.save(token);
        //when
        //then
        assertThatThrownBy(() -> service.verifyExpiration(token))
                .isInstanceOf(TokenRefreshException.class)
                .hasMessageContaining(token.getRefreshToken(), "Refresh token was expired. Please make a new signin request");
    }

    @Test
    void givenUserAndToken_whenVerifyExpiration_thenReturnToken() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar");
        userRepository.save(user);
        token = new RefreshToken(user, UUID.randomUUID().toString(), Instant.now().plusMillis(6000L));
        refreshTokenRepository.save(token);
        //when
        var result = service.verifyExpiration(token);
        //then
        assertThat(result).isEqualTo(token);
    }
}