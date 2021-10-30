package com.github.tbquyen.changepassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class DefaultRedirect extends DefaultRedirectStrategy {
	private HttpSessionRequestCache requsetCache = new HttpSessionRequestCache();

	/**
	 * get redirect Url.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String getRedirectUrl(HttpServletRequest request, HttpServletResponse response, String defaultUrl) {
		SavedRequest rq = requsetCache.getRequest(request, response);
		requsetCache.removeRequest(request, response);

		if (rq == null)
			return defaultUrl;

		String redirectUrl = calculateRedirectUrl(request.getContextPath(), rq.getRedirectUrl());
		return response.encodeRedirectURL(redirectUrl);
	}
}
