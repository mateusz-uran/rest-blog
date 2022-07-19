package io.github.mateuszuran.restblog;

import io.github.mateuszuran.restblog.model.Role;
import io.github.mateuszuran.restblog.model.User;
import io.github.mateuszuran.restblog.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class RestBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestBlogApplication.class, args);
	}

	/*@Bean
	CommandLineRunner run(UserService service) {
		return args -> {
			service.saveRole(new Role(null, "ROLE_ADMIN"));
			service.saveRole(new Role(null, "ROLE_USER"));

			service.saveUser(new User(null, "Mateusz Uranowski", "admin", "admin", new ArrayList<>()));
			service.saveUser(new User(null, "Jan Kowalski", "user", "user", new ArrayList<>()));

			service.addRoleToUser("admin", "ROLE_ADMIN");
			service.addRoleToUser("admin", "ROLE_USER");
			service.addRoleToUser("user", "ROLE_USER");
		};
	}*/

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
