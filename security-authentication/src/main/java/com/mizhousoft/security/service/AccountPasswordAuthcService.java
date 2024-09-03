package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.AccountPasswordToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 账号认证服务
 *
 * @version
 */
public interface AccountPasswordAuthcService
{
	/**
	 * 认证账号密码
	 * 
	 * @param token
	 * @param host
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	AccountDetails authenticate(AccountPasswordToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
