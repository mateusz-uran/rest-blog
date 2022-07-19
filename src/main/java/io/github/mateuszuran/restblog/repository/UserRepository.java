package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
