package com.github.tbquyen.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.github.tbquyen.config.WebPrincipal;

public class UsernamePasswordAuthenticationFilterEx extends UsernamePasswordAuthenticationFilter {
	private static final Logger LOGGER = LogManager.getLogger(UsernamePasswordAuthenticationFilterEx.class);
	private LoginValidator loginValidator;

	public UsernamePasswordAuthenticationFilterEx(LoginValidator loginValidator) {
		super();
		this.loginValidator = loginValidator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		// validate form
		LoginForm form = new LoginForm();
		form.setUsername(username);
		form.setPassword(password);
		form.setRememberme(request.getParameter("rememberme"));

		Errors errors = new BeanPropertyBindingResult(form, "LoginForm");
		loginValidator.validate(form, errors);

		if (errors.hasErrors()) {
			LOGGER.debug("Validate LoginForm Fail");
			throw new LoginAuthenticationException("Validate Fail", errors, form);
		}

		WebPrincipal authRequest = new WebPrincipal(username, password);
		// can setting multiple parameter

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
