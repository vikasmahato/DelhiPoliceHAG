package com.delhipolice.mediclaim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.delhipolice.mediclaim.repositories")
public class MediclaimApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediclaimApplication.class, args);
	}

}
