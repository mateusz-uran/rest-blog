package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.exception.TokenRefreshException;
import io.github.mateuszuran.restblog.model.ERole;
import io.github.mateuszuran.restblog.model.RefreshToken;
import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.payload.request.SignupRequest;
import io.github.mateuszuran.restblog.payload.request.TokenRefreshRequest;
import io.github.mateuszuran.restblog.payload.response.TokenRefreshResponse;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import io.github.mateuszuran.restblog.security.jwt.JwtUtils;
import io.github.mateuszuran.restblog.security.services.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder encoder, final JwtUtils jwtUtils, final RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    public boolean checkIfUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkIfUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void signUp(SignupRequest signupRequest) {
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getGender(),
                signupRequest.getAvatar());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

/*    public JwtResponse auth(LoginRequest loginRequest) {
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
        return new JwtResponse(jwt, refreshToken.getRefreshToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getAvatar(), userDetails.getEmail(), roles);
    }*/

/*    public TokenRefreshResponse token(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }*/
}
