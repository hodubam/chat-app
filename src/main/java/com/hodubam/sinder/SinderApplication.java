package com.hodubam.sinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class SinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.hodubam.sinder.SinderApplication.class, args);
	}

}
