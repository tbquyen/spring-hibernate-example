package com.github.tbquyen.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.tbquyen.entity.CategoriesMaster;

@Service
public class CategoriesValidator implements Validator {
	@Autowired
	private CategoriesService categoriesService;

	/**
	 * This Validator validates *just* CategoriesForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return CategoriesForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "ERROR.001", new String[] { "Cattegory " });

		if (errors.hasErrors())
			return;

		CategoriesForm form = (CategoriesForm) target;
		CategoriesMaster categoriesMaster = categoriesService.getCategory(form.getName());
		if (categoriesMaster != null) {
			errors.rejectValue("name", "ERROR.002", new String[] { "cattegory" }, null);
		}
	}
}
