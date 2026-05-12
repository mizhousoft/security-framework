package com.mizhousoft.security.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.mizhousoft.security.http.CachedBodyRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 请求Body缓存过滤器
 *
 */
public class CachedBodyRequestFilter extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		CachedBodyRequestWrapper wrapper = new CachedBodyRequestWrapper(request);

		filterChain.doFilter(wrapper, response);
	}
}