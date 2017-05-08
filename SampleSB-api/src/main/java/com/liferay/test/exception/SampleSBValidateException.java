package com.liferay.test.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public class SampleSBValidateException extends PortalException {

	protected List<String> _errors = null;
	
	public SampleSBValidateException() {}
	
	public SampleSBValidateException(List<String> errors) {
		_errors = errors;
	}	
	
	public List<String> getErrors() {
		return _errors;
	}
}
