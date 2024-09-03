package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.AppleToken;
import com.mizhousoft.security.service.AppleAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 苹果认证过滤器
 * 
 * @version
 */
public class AppleAuthcFilter extends AuthenticationFilter
{
	private AppleAuthcService appleAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		AppleToken token = createToken(request, response, AppleToken.class);

		return appleAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置appleAuthcService
	 * 
	 * @param appleAuthcService
	 */
	public void setAppleAuthcService(AppleAuthcService appleAuthcService)
	{
		this.appleAuthcService = appleAuthcService;
	}
}
