package com.karpo.quisy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QuisyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuisyApplication.class, args);
	}

}
