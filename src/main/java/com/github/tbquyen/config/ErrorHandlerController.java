package com.github.tbquyen.config;

import java.util.Locale;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class ErrorHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ErrorHandlerController.class);
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/400.html")
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView renderError400Page(ModelMap modelMap) {
		modelMap.addAttribute("errorCode", 400);
		modelMap.addAttribute("msg", messageSource.getMessage("error.404", null, Locale.getDefault()));
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/403.html")
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ModelAndView renderError403Page(ModelMap modelMap) {
		modelMap.addAttribute("errorCode", 403);
		modelMap.addAttribute("msg", messageSource.getMessage("error.403", null, Locale.getDefault()));
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/404.html")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView renderError404Page(ModelMap modelMap) {
		modelMap.addAttribute("errorCode", 404);
		modelMap.addAttribute("msg", messageSource.getMessage("error.404", null, Locale.getDefault()));
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/405.html")
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ModelAndView renderError405Page(ModelMap modelMap) {
		modelMap.addAttribute("errorCode", 405);
		modelMap.addAttribute("msg", messageSource.getMessage("error.503", null, Locale.getDefault()));
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/503.html")
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public ModelAndView renderError503Page(ModelMap modelMap) {
		modelMap.addAttribute("errorCode", 503);
		modelMap.addAttribute("msg", messageSource.getMessage("error.503", null, Locale.getDefault()));
		return new ModelAndView("error");
	}

	@RequestMapping(value = "/500.html")
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView renderError500Page(ModelMap modelMap, ServletRequest request) {
		Object ex = request.getAttribute(ErrorHandlerFilter.ERROR_HANDLER_FILTER);
		if(ex instanceof Exception) {
			LOGGER.error(ex);
			modelMap.addAttribute("exception", ex);
		}

		modelMap.addAttribute("errorCode", 500);
		modelMap.addAttribute("msg", messageSource.getMessage("error.500", null, Locale.getDefault()));
		return new ModelAndView("error");
	}
}
