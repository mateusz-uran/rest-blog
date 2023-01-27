package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.ERole;
import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.payload.request.SignupRequest;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import io.github.mateuszuran.restblog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AuthService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void initDefaultRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        roles.add(new Role(ERole.ROLE_ADMIN));
        var savedRoles = roleRepository.findAll();
        var savedRolesToSet = new HashSet<>(savedRoles);

        if(savedRoles.stream().noneMatch(savedRolesToSet::contains)) {
            roleRepository.saveAll(roles);
        }
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
}
