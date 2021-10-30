/**
 * 
 */
package com.github.tbquyen.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.github.tbquyen.login.LoginValidator;
import com.github.tbquyen.login.UserDetailsAuthenticationProviderExt;
import com.github.tbquyen.login.UserDetailsServiceImpl;
import com.github.tbquyen.login.UsernamePasswordAuthenticationFilterEx;

/**
 * @author QUYENTB
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private LoginValidator loginValidator;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy() {
		ConcurrentSessionControlAuthenticationStrategy concurrentSession = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
		concurrentSession.setMaximumSessions(1);
		// If set to true, a SessionAuthenticationException will be raised which meansthe user authenticating will be prevented from authenticating.
		// if set to false, the user that has already authenticated will be forcibly loggedout.
		concurrentSession.setExceptionIfMaximumExceeded(false);
		SessionFixationProtectionStrategy sessionFixation = new SessionFixationProtectionStrategy();
		RegisterSessionAuthenticationStrategy registerSession = new RegisterSessionAuthenticationStrategy(sessionRegistry());
		CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(Arrays.asList(concurrentSession, sessionFixation, registerSession));
		return sessionAuthenticationStrategy;
	}

	@Bean
	public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilterEx(loginValidator);
		filter.setAuthenticationManager(super.authenticationManagerBean());
		filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
		// prevented from authenticating
		filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?expired"));
		// create RememberMe Cookie
		filter.setRememberMeServices(rem());
		return filter;
	}

	@Bean
	public TokenBasedRememberMeServices rem() {
		TokenBasedRememberMeServices rmb = new TokenBasedRememberMeServices(
				TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, userDetailsServiceImpl);
		rmb.setParameter("rememberme");
		rmb.setTokenValiditySeconds(TokenBasedRememberMeServices.TWO_WEEKS_S);
		rmb.setCookieName("REMEMBERME");
		return rmb;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http.csrf().disable();
		http.anonymous().authorities("ROLE_ANONYMOUS");
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/changePassword").permitAll()
			.antMatchers("/users/*").hasAnyRole("MENTOR")
			.antMatchers("/users").hasAnyRole("MENTOR")
			.antMatchers("/categories/*").hasAnyRole("MENTOR")
			.antMatchers("/categories").hasAnyRole("MENTOR")
			.antMatchers("/questions/*").hasAnyRole("MENTOR")
			.antMatchers("/questions").hasAnyRole("MENTOR")
			.antMatchers("/quizreport/*").hasAnyRole("MENTOR")
			.antMatchers("/quizreport").hasAnyRole("MENTOR")
			.antMatchers("/quiz/*").hasAnyRole("MENTOR", "MEMBER")
			.antMatchers("/quiz").hasAnyRole("MENTOR", "MEMBER")
			.anyRequest().authenticated();

		http.formLogin()
			.loginPage("/login")
			.permitAll()
			.loginProcessingUrl("/login");

		http.logout()
			.logoutUrl("/logout")
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/login?logout")
			.permitAll();

		http.sessionManagement()
			.maximumSessions(1) // not apply
			// session expired
			.expiredUrl("/login?expired")
			.sessionRegistry(sessionRegistry());

		// autoLogin
		http.rememberMe().rememberMeServices(rem());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new UserDetailsAuthenticationProviderExt(userDetailsServiceImpl, passwordEncoder()));
	}
}
