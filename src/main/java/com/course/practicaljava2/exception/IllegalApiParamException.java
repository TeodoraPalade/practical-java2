package com.course.practicaljava2.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // the ex can be thrown globally, not only in it's own controller
public class IllegalApiParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141364279848951166L;

	public IllegalApiParamException(String message) {
		super(message);
	}

}
