package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.ERole;
import io.github.mateuszuran.restblog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
