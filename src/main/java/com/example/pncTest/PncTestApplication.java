package com.example.pncTest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PncTestApplication {

	public static void main(String[] args) {

		SpringApplication.run(PncTestApplication.class, args);

		System.out.println("hello");
	}
}
