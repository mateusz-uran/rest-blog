package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void givenUserObject_whenFindByUsername_thenReturnUser() {
        //given
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar_john"
        );
        repository.save(user);
        //when
        var result = repository.findByUsername(user.getUsername()).orElseThrow();
        //then
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void givenUserObject_whenFindByUsername_thenReturnNotFound() {
        //given
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar_john"
        );
        repository.save(user);
        //when
        var result = repository.findByUsername(user.getUsername()).orElseThrow();
        //then
        assertThat(result.getUsername()).isNotEqualTo("foo");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void givenUserObject_whenExistsByUsername_thenReturnUser() {
        //given
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar_john"
        );
        repository.save(user);
        //when
        var result = repository.existsByUsername(user.getUsername());
        //then
        assertTrue(result);
    }

    @Test
    void givenUserObject_whenExistsByUsername_thenReturnFalse() {
        //given
        User userNotFound = new User(
                2L,
                "Adam",
                "adam@gmail.com",
                "adam123",
                "male",
                "avatar_adam"
        );
        //when
        var result = repository.existsByUsername(userNotFound.getUsername());
        //then
        assertFalse(result);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void givenUserObject_whenExistsByEmail_thenReturnUser() {
        //given
        User user = new User(
                1L,
                "John",
                "john@gmail.com",
                "john123",
                "male",
                "avatar_john"
        );
        repository.save(user);
        //when
        var result = repository.existsByEmail(user.getEmail());
        //then
        assertTrue(result);
    }

    @Test
    void givenUserObject_whenExistsByEmail_thenReturnFalse() {
        //given
        User userNotFound = new User(
                2L,
                "Adam",
                "adam@gmail.com",
                "adam123",
                "male",
                "avatar_adam"
        );
        //when
        var result = repository.existsByEmail(userNotFound.getEmail());
        //then
        assertFalse(result);
    }
}