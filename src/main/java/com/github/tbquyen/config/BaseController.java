package com.github.tbquyen.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseController {
	protected static final Logger LOGGER = LogManager.getLogger(BaseController.class);
	private static final String BINDINGRESULT = "org.springframework.validation.BindingResult.%s";
	protected static final String SUCCESS = "successMessage";
	protected static final String ERROR = "errorMessage";
	protected static final String WARNING = "warningMessage";

	protected void loadRedirectErrors(ModelMap modelMap, String formName, String resultName) {
		Assert.isTrue(!String.format(BINDINGRESULT, formName).equals(resultName), "Result Name is not valid");

		if (modelMap.containsAttribute(resultName)) {
			modelMap.addAttribute(String.format(BINDINGRESULT, formName), modelMap.getAttribute(resultName));
		}
	}

	protected void redirectErrors(RedirectAttributes redirectAttributes, String formName, Object form,
			String resultName, Object result) {
		Assert.isTrue(!String.format(BINDINGRESULT, formName).equals(resultName), "Result Name is not valid");
		redirectAttributes.addFlashAttribute(formName, form);
		redirectAttributes.addFlashAttribute(resultName, result);
	}
}
