package com.nobblecrafts.user;

import java.util.UUID;
import java.util.stream.Stream;

import com.nobblecrafts.user.models.User;
import com.nobblecrafts.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class UserApplication {


	@Bean
	@Profile("!test")
	CommandLineRunner Users(UserRepository repository, @Value("${amqp.exchange.userstatus}") final String ex) {
		System.out.println("USERAPPLICATION-EXCHANGE: " + ex);
		return a -> {
			repository.deleteAll().subscribe(null, null, () -> {
				Stream.of(new User("6abg6707ghi31", "userAAA", "aaa@nobblecrafts.com"),
						new User(UUID.randomUUID().toString(), "userBBB", "bbb@nobblecrafts.com"),
						new User(UUID.randomUUID().toString(), "userCCC", "ccc@nobblecrafts.com"),
						new User(UUID.randomUUID().toString(), "userDDD", "ddd@nobblecrafts.com")).forEach(user -> {
							repository.save(user).subscribe(System.out::println);
						});
			});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
