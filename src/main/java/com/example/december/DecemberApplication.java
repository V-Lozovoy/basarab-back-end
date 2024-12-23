package com.example.december;

import  org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DecemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecemberApplication.class, args);
	}

}
