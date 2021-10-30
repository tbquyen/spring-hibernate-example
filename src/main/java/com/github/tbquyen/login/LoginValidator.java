package com.github.tbquyen.login;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class LoginValidator implements Validator {

	/**
	 * This Validator validates *just* LoginForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return LoginForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "ERROR.001", new String[] { "userName" });
		ValidationUtils.rejectIfEmpty(errors, "password", "ERROR.001", new String[] { "password" });
	}
}
