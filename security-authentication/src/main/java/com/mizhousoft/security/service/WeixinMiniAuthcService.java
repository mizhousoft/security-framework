package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.request.WeixinMiniToken;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 微信小程序认证服务
 *
 * @version
 */
public interface WeixinMiniAuthcService
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
	AccountDetails authenticate(WeixinMiniToken token, String host, HttpServletRequest request) throws AuthenticationException;
}
