/**
 * 
 */
package com.github.tbquyen.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author QUYENTB
 *
 */
public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
}
