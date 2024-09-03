package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.WeixinMPToken;
import com.mizhousoft.security.service.WeixinMPAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 微信公众号认证过滤器
 *
 * @version
 */
public class WeixinMPAuthcFilter extends AuthenticationFilter
{
	private WeixinMPAuthcService weixinMPAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		WeixinMPToken token = createToken(request, response, WeixinMPToken.class);

		return weixinMPAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置weixinMPAuthcService
	 * 
	 * @param weixinMPAuthcService
	 */
	public void setWeixinMPAuthcService(WeixinMPAuthcService weixinMPAuthcService)
	{
		this.weixinMPAuthcService = weixinMPAuthcService;
	}
}
