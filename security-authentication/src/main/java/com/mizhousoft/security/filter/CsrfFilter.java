package com.mizhousoft.security.filter;

import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.commons.web.util.WebUtils;
import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.Authentication;
import com.mizhousoft.security.SecurityConstants;
import com.mizhousoft.security.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RefererFilter
 *
 */
public class CsrfFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(CsrfFilter.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		if (isRequestExcluded(request))
		{
			filterChain.doFilter(request, response);

			return;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication)
		{
			accessUnauthorized(request, response);

			return;
		}

		AccountDetails accountDetails = authentication.getPrincipal();
		String csrfToken = accountDetails.getCsrfToken();

		String requestCsrfToken = request.getHeader(SecurityConstants.HEADER_X_CSRF_TOKEN);
		if (csrfToken.equals(requestCsrfToken))
		{
			filterChain.doFilter(request, response);
		}
		else
		{
			String requestPath = WebUtils.getPathWithinApplication(request);
			requestPath = URLEncoder.encode(requestPath, CharEncoding.UTF8);

			LOG.error("Request csrf token is invalid, path is {}.", requestPath);

			redirectToLogin(request, response);
		}
	}
}
