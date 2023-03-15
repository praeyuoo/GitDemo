package com.exercise.zkspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@ComponentScan(basePackages = "com.exercise.zkspringboot")
@SpringBootApplication
@Controller
public class ZkspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZkspringbootApplication.class, args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@GetMapping("/home")
	public String consentFormExample() {
		return "consent-form";
	}

}
