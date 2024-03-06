package com.delhipolice.mediclaim;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories("com.delhipolice.mediclaim.repositories")
public class MediclaimApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediclaimApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		//dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty String to null
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
