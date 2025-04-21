package com.koinkapp.koink_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KoinkAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(KoinkAppApplication.class, args);
	}
}

