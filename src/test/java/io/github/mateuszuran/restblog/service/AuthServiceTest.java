package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.ERole;
import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.payload.request.SignupRequest;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import io.github.mateuszuran.restblog.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService service;
    private User user;
    @Mock
    private UserDetailsImpl userDetails;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;

    @Test
    void checkIfUserExistsByUsername() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar"
        );
        userRepository.save(user);
        //when
        when(service.checkIfUserExistsByUsername(user.getUsername())).thenReturn(true);
        //then
        assertTrue(service.checkIfUserExistsByUsername(user.getUsername()));
    }

    @Test
    void checkIfUserExistsByEmail() {
        //given
        user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar"
        );
        userRepository.save(user);
        //when
        when(service.checkIfUserExistsByEmail(user.getEmail())).thenReturn(true);
        //then
        assertTrue(service.checkIfUserExistsByEmail(user.getEmail()));
    }

    @Test
    void signUp() {
        //given
        SignupRequest signupRequest = new SignupRequest(
                "John",
                "john@gmail.com",
                encoder.encode("john123"),
                "male",
                "avatar");
        Role role = new Role(ERole.ROLE_USER);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        //when
        service.signUp(signupRequest);
        //then
        verify(userRepository).save(any(User.class));
    }
}