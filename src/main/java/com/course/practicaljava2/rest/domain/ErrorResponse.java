package com.course.practicaljava2.rest.domain;

public class ErrorResponse {

	private String errorMessage;
	private long timestamp;

	public ErrorResponse() {

	}

	public ErrorResponse(String errorMessage, long timestamp) {
		super();
		this.errorMessage = errorMessage;
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorMessage=" + errorMessage + ", timestamp=" + timestamp + "]";
	}
}
