package com.beta.limited;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LimitedApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitedApplication.class, args);
	}

}
