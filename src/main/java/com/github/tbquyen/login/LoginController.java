package com.github.tbquyen.login;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tbquyen.config.WebPrincipal;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@GetMapping
	public ModelAndView get(ModelMap modelMap, @ModelAttribute("LoginForm") LoginForm form, BindingResult result,
			HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("login");
		mv.setStatus(HttpStatus.UNAUTHORIZED);

		if (WebPrincipal.getInstance().isAuthenticated()) {
			LOGGER.debug("Is Login with acccount: " + WebPrincipal.getInstance().getName());
			mv.setViewName("redirect:/");
			return mv;
		}

		Object authenticationException = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (authenticationException instanceof PasswordExpiredException) {
			LOGGER.debug("Login Fail: PasswordExpiredException");
			PasswordExpiredException exception = (PasswordExpiredException) authenticationException;
			BeanUtils.copyProperties(exception.getLoginForm(), form);
			mv.setViewName("forward:changePassword");
			return mv;
		}

		if (authenticationException instanceof LoginAuthenticationException) {
			LOGGER.debug("Login Fail: LoginAuthenticationException");
			LoginAuthenticationException exception = (LoginAuthenticationException) authenticationException;
			result.addAllErrors(exception.getErrors());
			BeanUtils.copyProperties(exception.getLoginForm(), form);
			mv.setStatus(HttpStatus.BAD_REQUEST);
		}

		if (authenticationException instanceof SessionAuthenticationException) {
			LOGGER.debug("Login Fail: SessionAuthenticationException");
			result.reject("login.002");
		}

		return mv;
	}
}
