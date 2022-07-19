package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
