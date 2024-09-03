package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.AccountPasswordToken;
import com.mizhousoft.security.service.AccountPasswordAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 账号密码认证过滤器
 * 
 * @version
 */
public class AccountPasswordAuthcFilter extends AuthenticationFilter
{
	private AccountPasswordAuthcService accountAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		AccountPasswordToken token = createToken(request, response, AccountPasswordToken.class);

		return accountAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置accountAuthcService
	 * 
	 * @param accountAuthcService
	 */
	public void setAccountAuthcService(AccountPasswordAuthcService accountAuthcService)
	{
		this.accountAuthcService = accountAuthcService;
	}
}
