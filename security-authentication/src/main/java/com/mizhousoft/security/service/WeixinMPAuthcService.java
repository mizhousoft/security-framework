package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.WeixinMPToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 微信公众号认证服务
 *
 * @version
 */
public interface WeixinMPAuthcService
{
	/**
	 * 认证微信公众号登录
	 * 
	 * @param token
	 * @param host
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	AccountDetails authenticate(WeixinMPToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
