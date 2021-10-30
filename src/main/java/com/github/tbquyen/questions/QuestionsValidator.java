package com.github.tbquyen.questions;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class QuestionsValidator implements Validator {
	/**
	 * This Validator validates *just* CategoriesForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return QuestionsForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "id", "ERROR.001", new String[] { "Cattegory" });
		ValidationUtils.rejectIfEmpty(errors, "question", "ERROR.001", new String[] { "Question" });
		ValidationUtils.rejectIfEmpty(errors, "optionA", "ERROR.001", new String[] { "OptionA" });
		ValidationUtils.rejectIfEmpty(errors, "optionB", "ERROR.001", new String[] { "OptionA" });
		ValidationUtils.rejectIfEmpty(errors, "duration", "ERROR.001", new String[] { "Duration" });
		ValidationUtils.rejectIfEmpty(errors, "anser", "ERROR.001", new String[] { "Anser" });
	}
}
