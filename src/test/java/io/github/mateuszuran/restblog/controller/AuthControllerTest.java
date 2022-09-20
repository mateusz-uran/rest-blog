package io.github.mateuszuran.restblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mateuszuran.restblog.model.ERole;
import io.github.mateuszuran.restblog.model.RefreshToken;
import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.payload.request.LoginRequest;
import io.github.mateuszuran.restblog.payload.request.SignupRequest;
import io.github.mateuszuran.restblog.repository.RefreshTokenRepository;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private PasswordEncoder encoder;
    private final String URL = "/api/v1";

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void authenticateUser() throws Exception {
        //given
        Role role = new Role(ERole.ROLE_USER);
        roleRepository.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = new User(
                1L,
                "John",
                "john@o2.pl",
                encoder.encode("john123"),
                "male",
                "avatar",
                roles
        );
        userRepository.save(user);
        LoginRequest loginRequest = new LoginRequest(
                "John",
                "john123"
        );
        //when
        mockMvc.perform(post(URL + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                //then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenSingUpRequest_whenRegister_thenReturnStatus200() throws Exception {
        //given
        Role role = new Role(ERole.ROLE_USER);
        roleRepository.save(role);
        SignupRequest signupRequest = new SignupRequest(
                "Matt",
                "matt@gmail.com",
                "matt123",
                "male",
                "avatar");
        //when
        mockMvc.perform(post(URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                //then
                .andDo(print())
                .andExpect(status().isOk());
        var result = userRepository.findByUsername(signupRequest.getUsername()).orElseThrow();
        assertThat(result.getEmail()).isEqualTo(signupRequest.getEmail());
    }

    @Test
    void givenSingUpRequest_whenRegisterWithExistingUsername_thenReturnStatus400() throws Exception {
        //given
        Role role = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(role);
        User user = new User(
                1L,
                "John",
                "foo@gmail.com",
                "foo123",
                "male",
                "avatar"
        );
        userRepository.save(user);
        SignupRequest signupRequest = new SignupRequest(
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar");
        //when
        mockMvc.perform(post(URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                //then
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenSingUpRequest_whenRegisterWithExistingEmail_thenReturnStatus400() throws Exception {
        //given
        Role role = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(role);
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar"
        );
        userRepository.save(user);
        SignupRequest signupRequest = new SignupRequest(
                "foo",
                "john@gmail.com",
                "foo123",
                "male",
                "avatar");
        //when
        mockMvc.perform(post(URL + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                //then
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenRefreshToken_whenRefresh_thenReturnStatus200() throws Exception {
        //given
        User user = new User(
                1L,
                "John",
                "john@o2.pl",
                "john123",
                "male",
                "avatar");
        userRepository.save(user);
        RefreshToken token = new RefreshToken(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(6000)
        );
        refreshTokenRepository.save(token);
        //when
        mockMvc.perform(post(URL + "/refreshtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(token.getRefreshToken())))
                //then
                .andDo(print())
                .andExpect(status().isOk());
    }
}