package com.fods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HomePageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomePageServiceApplication.class, args);
	}

}
