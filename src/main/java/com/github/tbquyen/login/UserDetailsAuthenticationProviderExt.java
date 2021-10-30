package com.github.tbquyen.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.github.tbquyen.config.WebPrincipal;

public class UserDetailsAuthenticationProviderExt extends AbstractUserDetailsAuthenticationProvider {
	private static final Logger LOGGER = LogManager.getLogger(UserDetailsAuthenticationProviderExt.class);
	private UserDetailsServiceImpl userDetailsServiceImpl;
	private PasswordEncoder passwordEncoder;

	public UserDetailsAuthenticationProviderExt(UserDetailsServiceImpl userDetailsServiceImpl,
			PasswordEncoder passwordEncoder) {
		super();
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		LOGGER.debug("additionalAuthenticationChecks: " + userDetails.getUsername());

		UserDetailslmpl userDetailslmp = (UserDetailslmpl) userDetails;

		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(presentedPassword, userDetailslmp.getPassword())) {
			LOGGER.debug("Password Fail");
			LoginForm form = new LoginForm();
			form.setUsername(userDetailslmp.getUsername());
			form.setPassword(presentedPassword);
			Errors errors = new BeanPropertyBindingResult(form, "LoginForm");
			errors.reject("login.001");
			throw new LoginAuthenticationException("Password fail", errors, form);
		}

		if (!userDetailslmp.isCredentialsNonExpired()) {
			LOGGER.debug("Change password");
			LoginForm form = new LoginForm();
			form.setUsername(userDetailslmp.getUsername());
			form.setPassword(presentedPassword);
			throw new PasswordExpiredException("Change password", null, form);
		}
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		LOGGER.debug("retrieveUser: " + username);

		UserDetailslmpl loadedUser;

		try {
			loadedUser = (UserDetailslmpl) this.userDetailsServiceImpl.loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			loadedUser = null;
		} catch (Exception repositoryProblem) {
			LOGGER.debug("repository problem : " + repositoryProblem);
			loadedUser = null;
		}

		if (loadedUser == null) {
			LOGGER.debug("Username not found: " + username);
			LoginForm form = new LoginForm();
			form.setUsername(username);
			Errors errors = new BeanPropertyBindingResult(form, "LoginForm");
			errors.reject("login.001");
			throw new LoginAuthenticationException("Username not found", errors, form);
		}

		return loadedUser;
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		LOGGER.debug("Login Success Authentication: " + user.getUsername());
		Authentication token = super.createSuccessAuthentication(principal, authentication, user);

		WebPrincipal result = new WebPrincipal(token.getPrincipal(), token.getCredentials(), token.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}
}
