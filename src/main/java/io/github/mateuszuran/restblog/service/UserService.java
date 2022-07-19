package io.github.mateuszuran.restblog.service;

import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}
