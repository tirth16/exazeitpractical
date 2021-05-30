package com.exazeit.practical.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Common Class for all kind of error responses
 *
 * @author Tirth Thakkar,Ronak Patel
 *
 */
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	private String exception;

	@JsonProperty("error_code")
	private Integer errorCode;

	@JsonProperty("error_message")
	private String errorMessage;

	@JsonProperty("debug_message")
	private String debugMessage;

	@JsonProperty("debug_message_list")
	private List<String> debugMessageList;

	@JsonProperty("sub_error_list")
	private List<SubError> subErrorList;

	@JsonProperty("error_stack_trace")
	private Object errorStackTrace;

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<SubError> getSubErrorList() {
		return subErrorList;
	}

	public void setSubErrorList(List<SubError> subErrorList) {
		this.subErrorList = subErrorList;
	}

	public List<String> getDebugMessageList() {
		return debugMessageList;
	}

	public void setDebugMessageList(List<String> debugMessageList) {
		this.debugMessageList = debugMessageList;
	}

	public Object getErrorStackTrace() {
		return errorStackTrace;
	}

	public void setErrorStackTrace(Object errorStackTrace) {
		this.errorStackTrace = errorStackTrace;
	}

	public void addSubErrorMessage(List<FieldError> fieldErrors) {
		if (subErrorList == null) {
			subErrorList = new ArrayList<>();
		}
		for (FieldError fieldError : fieldErrors) {
			SubError subError = new SubError(fieldError.getObjectName(), fieldError.getField(),
					fieldError.getRejectedValue(), fieldError.getDefaultMessage());
			subErrorList.add(subError);
		}
		this.setSubErrorList(subErrorList);
	}

	public void addSubErrorMessageForUniqueConstraintViolation(List<ObjectError> objectErrors) {
		if (subErrorList == null) {
			subErrorList = new ArrayList<>();
		}
		for (ObjectError objectError : objectErrors) {
			System.out.println("Object Error" + objectError);
			SubError subError = new SubError(objectError.getObjectName(), objectError.getArguments()[1].toString(),
					objectError.getCode(), objectError.getDefaultMessage());
			subErrorList.add(subError);
		}
		this.setSubErrorList(subErrorList);
	}

	public void addSubErrorMessageForConstraintViolation(Set<ConstraintViolation<?>> constraintViolations) {
		if (subErrorList == null) {
			subErrorList = new ArrayList<>();
		}

		for (ConstraintViolation<?> cv : constraintViolations) {
			SubError subError = new SubError(cv.getRootBeanClass().getSimpleName(),
					((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
			subErrorList.add(subError);
		}
		this.setSubErrorList(subErrorList);
	}

}
