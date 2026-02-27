package com.bnroll.tm.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "com.bnroll.tm.user"})
public class TmAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmAuthServiceApplication.class, args);
	}

}
