package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.VerificationCodeToken;
import com.mizhousoft.security.service.VerificationCodeAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 验证码认证过滤器
 *
 * @version
 */
public class VerificationCodeAuthcFilter extends AuthenticationFilter
{
	private VerificationCodeAuthcService verificationCodeAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		VerificationCodeToken token = createToken(request, response, VerificationCodeToken.class);

		return verificationCodeAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置verificationCodeAuthcService
	 * 
	 * @param verificationCodeAuthcService
	 */
	public void setVerificationCodeAuthcService(VerificationCodeAuthcService verificationCodeAuthcService)
	{
		this.verificationCodeAuthcService = verificationCodeAuthcService;
	}
}
