package io.github.mateuszuran.restblog.repository;

import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static io.github.mateuszuran.restblog.model.ERole.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Test
    void givenRoleObject_whenFindByName_thenReturnRole() {
        //given
        Role role = new Role(
                ROLE_USER
        );
        repository.save(role);
        //when
        var result = repository.findByName(role.getName()).orElseThrow();
        //then
        assertThat(result).isEqualTo(role);
    }
}