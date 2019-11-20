package com.course.practicaljava2.rest.controller;

import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultRestController {

	@GetMapping("/welcome")
	public String welcome() {
		System.out.println(StringUtils.join("Hello ", "This is ", "Sprifng Boot2 ", "REST API!"));
		return ("Welcome to Spring Boot");
	}

	@GetMapping("/time")
	public String time() {
		return LocalTime.now().toString();
	}
}
