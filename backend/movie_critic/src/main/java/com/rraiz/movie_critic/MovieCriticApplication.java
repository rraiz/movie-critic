package com.rraiz.movie_critic;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rraiz.movie_critic.feature.user.model.ApplicationUser;
import com.rraiz.movie_critic.feature.user.model.Role;
import com.rraiz.movie_critic.feature.user.repository.RoleRepository;
import com.rraiz.movie_critic.feature.user.repository.UserRepository;

@SpringBootApplication
public class MovieCriticApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCriticApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder password) {
		return args -> {

			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser(1, "admin", password.encode("password"), roles);

			userRepository.save(admin);

		};	
	}

}
