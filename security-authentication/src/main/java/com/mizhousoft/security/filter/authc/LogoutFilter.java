package com.mizhousoft.security.filter.authc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.security.Authentication;
import com.mizhousoft.security.context.SecurityContextHolder;
import com.mizhousoft.security.filter.AccessControlFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 定制注销过滤器
 *
 * @version
 */
public class LogoutFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(LogoutFilter.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		try
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (null == authentication)
			{
				LOG.info("Account already logout.");
			}
			else
			{
				filterChain.doFilter(request, response);
			}

			destroySession(request, response, true);

			LOG.info("Account logout successfully, id is {}, name is {}.", authentication.getAccountId(), authentication.getName());
		}
		catch (Throwable e)
		{
			LOG.error("Logout failed.", e);
		}
	}
}
