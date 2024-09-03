package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.WeixinOpenToken;
import com.mizhousoft.security.service.WeixinOpenAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 微信开放平台认证过滤器
 *
 * @version
 */
public class WeixinOpenAuthcFilter extends AuthenticationFilter
{
	private WeixinOpenAuthcService weixinOpenAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		WeixinOpenToken token = createToken(request, response, WeixinOpenToken.class);

		return weixinOpenAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置weixinOpenAuthcService
	 * 
	 * @param weixinOpenAuthcService
	 */
	public void setWeixinOpenAuthcService(WeixinOpenAuthcService weixinOpenAuthcService)
	{
		this.weixinOpenAuthcService = weixinOpenAuthcService;
	}
}
