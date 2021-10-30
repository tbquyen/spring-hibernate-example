/**
 * 
 */
package com.github.tbquyen.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author QUYENTB
 *
 */
public class WebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Bean
	private ErrorHandlerFilter filter() {
		return new ErrorHandlerFilter();
	};

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { filter() };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
