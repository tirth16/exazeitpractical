package com.exazeit.practical.enumration;

public enum ErrorEnum {

	NO_RECORD_FOUND(1001, "No record Found."),
	MOBILE_NUMBER_ALREADY_IN_USE(2001, "Mobile Number is already in use please use different one"),
	IDNUMBER_ALREADY_IN_USE(2002, "Id number already in use"),
	IDNUMBER_IS_NOT_VALID(2003, "Please enter valid id number"),;

	private final int errorCode;
	private final String errorType;
	private String errorMessage;

	ErrorEnum(int errorCode, String defaultMessage) {
		errorType = "APPLICATION";
		this.errorCode = errorCode;
		errorMessage = defaultMessage;
	}

	ErrorEnum(String errorType, int errorCode, String defaultMessage) {
		this.errorType = errorType;
		this.errorCode = errorCode;
		errorMessage = defaultMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
