package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.service.CowinService;

@SpringBootApplication
public class CoWinNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoWinNotifierApplication.class, args);

	}
}
