package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.WeixinOpenToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 微信开放平台认证服务
 *
 * @version
 */
public interface WeixinOpenAuthcService
{
	/**
	 * 登录
	 * 
	 * @param token
	 * @param host
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	AccountDetails authenticate(WeixinOpenToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
