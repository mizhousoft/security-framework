package com.mizhousoft.security.filter.authz;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.commons.web.util.WebUtils;
import com.mizhousoft.security.Authentication;
import com.mizhousoft.security.context.SecurityContextHolder;
import com.mizhousoft.security.filter.AccessControlFilter;
import com.mizhousoft.security.service.AccessControlService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 访问授权控制器
 * 
 * @version
 */
public class AccessAuthorizationFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AccessAuthorizationFilter.class);

	private AccessControlService accessControlService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		String requestPath = WebUtils.getPathWithinApplication(request);

		try
		{
			if (excludePaths.contains(requestPath))
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

			boolean continueChain = isAccessAllowed(requestPath, authentication, request, response);
			if (continueChain)
			{
				filterChain.doFilter(request, response);
			}
			else
			{
				accessDenied(request, response);
			}
		}
		catch (Throwable e)
		{
			LOG.error("Account can not access the request path, request path is " + requestPath + '.', e);
		}
	}

	private boolean isAccessAllowed(String requestPath, Authentication authentication, HttpServletRequest request,
	        HttpServletResponse response)
	{
		Set<String> roles = accessControlService.getRolesByPath(requestPath);
		if (CollectionUtils.isEmpty(roles))
		{
			LOG.error("Request path is not in any role, request path is " + requestPath + ".");
			return false;
		}

		Set<String> authRoles = authentication.getAuthorities();
		for (String role : roles)
		{
			if (authRoles.contains(role))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * 设置accessControlService
	 * 
	 * @param accessControlService
	 */
	public void setAccessControlService(AccessControlService accessControlService)
	{
		this.accessControlService = accessControlService;
	}
}
