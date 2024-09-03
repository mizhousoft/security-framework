package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.ByteDanceMiniToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 字节小程序认证服务
 *
 * @version
 */
public interface ByteDanceMiniAuthcService
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
	AccountDetails authenticate(ByteDanceMiniToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
