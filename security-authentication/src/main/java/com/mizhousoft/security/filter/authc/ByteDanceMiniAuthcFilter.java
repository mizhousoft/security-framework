package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.ByteDanceMiniToken;
import com.mizhousoft.security.service.ByteDanceMiniAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 字节小程序认证过滤器
 * 
 * @version
 */
public class ByteDanceMiniAuthcFilter extends AuthenticationFilter
{
	private ByteDanceMiniAuthcService byteDanceMiniAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		ByteDanceMiniToken token = createToken(request, response, ByteDanceMiniToken.class);

		return byteDanceMiniAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置byteDanceMiniAuthcService
	 * 
	 * @param byteDanceMiniAuthcService
	 */
	public void setByteDanceMiniAuthcService(ByteDanceMiniAuthcService byteDanceMiniAuthcService)
	{
		this.byteDanceMiniAuthcService = byteDanceMiniAuthcService;
	}
}
