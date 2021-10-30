package com.github.tbquyen.changepassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.tbquyen.entity.UserMaster;
import com.github.tbquyen.login.LoginDAO;

@Service
public class ChangePasswordValidator implements Validator {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private LoginDAO loginDAO;

	/**
	 * This Validator validates *just* ChangePasswordForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "ERROR.001", new String[] { "userName" });
		ValidationUtils.rejectIfEmpty(errors, "password", "ERROR.001", new String[] { "password" });
		ValidationUtils.rejectIfEmpty(errors, "newpassword", "ERROR.001", new String[] { "newpassword" });

		if (errors.hasErrors())
			return;

		ChangePasswordForm form = (ChangePasswordForm) target;
		UserMaster master = loginDAO.loadUserByUsername(form.getUsername());
		if (master == null || !passwordEncoder.matches(form.getPassword(), master.getPassword())) {
			errors.reject("login.001");
		}
	}
}
