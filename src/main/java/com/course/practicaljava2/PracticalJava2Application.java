package com.course.practicaljava2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticalJava2Application /* for deploy war - extends SpringBootServletInitializer */ {
// for deploy war - 
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(PracticalJava2Application.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(PracticalJava2Application.class, args);
		System.out.println("fgh");
	}

}
