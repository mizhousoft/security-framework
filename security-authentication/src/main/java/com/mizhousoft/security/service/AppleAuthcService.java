package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.AppleToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 苹果认证服务
 *
 * @version
 */
public interface AppleAuthcService
{
	/**
	 * 认证
	 * 
	 * @param token
	 * @param host
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	AccountDetails authenticate(AppleToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
