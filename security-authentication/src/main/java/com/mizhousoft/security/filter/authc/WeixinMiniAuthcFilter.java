package com.mizhousoft.security.filter.authc;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.filter.AuthenticationFilter;
import com.mizhousoft.security.request.WeixinMiniToken;
import com.mizhousoft.security.service.WeixinMiniAuthcService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 微信小程序认证过滤器
 *
 * @version
 */
public class WeixinMiniAuthcFilter extends AuthenticationFilter
{
	private WeixinMiniAuthcService weixinMiniAuthcService;

	@Override
	protected AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException
	{
		WeixinMiniToken token = createToken(request, response, WeixinMiniToken.class);

		return weixinMiniAuthcService.authenticate(token, host, request);
	}

	/**
	 * 设置weixinMiniAuthcService
	 * 
	 * @param weixinMiniAuthcService
	 */
	public void setWeixinMiniAuthcService(WeixinMiniAuthcService weixinMiniAuthcService)
	{
		this.weixinMiniAuthcService = weixinMiniAuthcService;
	}
}
