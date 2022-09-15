package io.github.mateuszuran.restblog.controller;

import io.github.mateuszuran.restblog.exception.TokenRefreshException;
import io.github.mateuszuran.restblog.model.RefreshToken;
import io.github.mateuszuran.restblog.payload.request.LoginRequest;
import io.github.mateuszuran.restblog.payload.request.SignupRequest;
import io.github.mateuszuran.restblog.payload.request.TokenRefreshRequest;
import io.github.mateuszuran.restblog.payload.response.JwtResponse;
import io.github.mateuszuran.restblog.payload.response.MessageResponse;
import io.github.mateuszuran.restblog.payload.response.TokenRefreshResponse;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import io.github.mateuszuran.restblog.security.jwt.JwtUtils;
import io.github.mateuszuran.restblog.security.services.RefreshTokenService;
import io.github.mateuszuran.restblog.security.services.UserDetailsImpl;
import io.github.mateuszuran.restblog.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    RefreshTokenService refreshTokenService;
    private AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getRefreshToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getAvatar(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (service.checkIfUserExistsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
        }
        if (service.checkIfUserExistsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken"));
        }
        service.signUp(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
