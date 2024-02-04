package com.zughead.reviewengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@SpringBootApplication
public class ReviewEngineApp {

	public static void main(String[] args) {
		SpringApplication.run(ReviewEngineApp.class, args);
	}

}
