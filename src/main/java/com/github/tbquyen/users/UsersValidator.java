package com.github.tbquyen.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.tbquyen.entity.UserMaster;

@Service
public class UsersValidator implements Validator {
	@Autowired
	private UsersService service;

	/**
	 * This Validator validates *just* CategoriesForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return UsersForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		UsersForm form = (UsersForm) target;

		ValidationUtils.rejectIfEmpty(errors, "username", "ERROR.001", new String[] { "Username" });
		ValidationUtils.rejectIfEmpty(errors, "role", "ERROR.001", new String[] { "Role" });

		if("+".equals(form.getAction())) {
			ValidationUtils.rejectIfEmpty(errors, "password", "ERROR.001", new String[] { "Password" });
		}

		if (errors.hasErrors())
			return;

		UserMaster userMaster = service.getUser(form.getUsername());
		if (userMaster != null) {
			errors.rejectValue("username", "ERROR.002", new String[] { "username" }, null);
		}
	}
}
