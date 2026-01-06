package com.mizhousoft.security.filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.security.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RefererFilter
 *
 */
public class RefererFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(RefererFilter.class);

	/**
	 * 支持的Referer
	 */
	private Set<String> supportReferers = Collections.emptySet();

	/**
	 * 构造函数
	 *
	 * @param supportReferers
	 */
	public RefererFilter(Set<String> supportReferers)
	{
		super();
		this.supportReferers = supportReferers;
	}

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

		String referer = request.getHeader(SecurityConstants.HEADER_REFERER);
		if (isRefererAllow(referer))
		{
			filterChain.doFilter(request, response);
		}
		else
		{
			redirectToLogin(request, response);
		}
	}

	private boolean isRefererAllow(String referer)
	{
		if (null == referer)
		{
			LOG.error("Referer is null.");
			return false;
		}

		try
		{
			URL url = URI.create(referer).toURL();
			String host = url.getHost();

			if (!supportReferers.contains(host))
			{
				referer = StringUtils.left(referer, 100);
				LOG.error("Referer is invalid, value is {}.", Base64.encodeBase64String(referer.getBytes(CharEncoding.UTF8)));

				return false;
			}
			else
			{
				return true;
			}
		}
		catch (MalformedURLException e)
		{
			referer = StringUtils.left(referer, 100);
			LOG.error("Referer is invalid, value is {}.", Base64.encodeBase64String(referer.getBytes(CharEncoding.UTF8)));

			return false;
		}
	}
}
