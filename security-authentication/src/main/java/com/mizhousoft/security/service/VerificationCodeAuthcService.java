package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.VerificationCodeToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 验证码认证服务
 *
 * @version
 */
public interface VerificationCodeAuthcService
{
	/**
	 * 认证帐号验证码
	 * 
	 * @param token
	 * @param host
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	AccountDetails authenticate(VerificationCodeToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
