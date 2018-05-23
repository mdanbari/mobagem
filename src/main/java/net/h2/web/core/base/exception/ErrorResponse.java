package net.h2.web.core.base.exception;

public class ErrorResponse {
	
	private String errorReferenceId;
	private String name;
	private String type;
	private String message;

	public String getErrorReferenceId() {
		return errorReferenceId;
	}

	public void setErrorReferenceId(String errorReferenceId) {
		this.errorReferenceId = errorReferenceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
