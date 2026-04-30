package com.coderdot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertyS
ource("classpath:application.properties")  // 👈 add this

public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
