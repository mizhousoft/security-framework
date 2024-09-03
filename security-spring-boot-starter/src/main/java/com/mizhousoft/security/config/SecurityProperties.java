package com.mizhousoft.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安全配置
 *
 * @version
 */
@Component
@ConfigurationProperties("security.authentication")
public class SecurityProperties
{
	/**
	 * 模式
	 * 值为cookie 或 token
	 */
	private String mode = "cookie";

	/**
	 * 环境
	 * 值为dev 或 prod
	 */
	private String environment = "prod";

	/**
	 * 帐号登录路径
	 * rest接口：/rest/v1/authc/accountLogin.action
	 */
	private String accountLogin = "/accountLogin.action";

	/**
	 * 验证码登录路径
	 * rest接口：/rest/v1/authc/verifyCodeLogin.action
	 */
	private String verifyCodeLogin = "/verifyCodeLogin.action";

	/**
	 * 微信公众号登录路径
	 * rest接口：/rest/v1/authc/weixinMPLogin.action
	 */
	private String weixinMPLogin = "/weixinMPLogin.action";

	/**
	 * 微信小程序登录路径
	 * rest接口：/rest/v1/authc/weixinMiniLogin.action
	 */
	private String weixinMiniLogin = "/weixinMiniLogin.action";

	/**
	 * 微信开放平台登录路径
	 * rest接口：/rest/v1/authc/weixinOpenLogin.action
	 */
	private String weixinOpenLogin = "/weixinOpenLogin.action";

	/**
	 * 苹果登录路径
	 * rest接口：/rest/v1/authc/appleLogin.action
	 */
	private String appleLogin = "/appleLogin.action";

	/**
	 * 字节小程序登录路径
	 * rest接口：/rest/v1/authc/byteDanceMiniLogin.action
	 */
	private String byteDanceMiniLogin = "/byteDanceMiniLogin.action";

	/**
	 * 退出路径
	 * rest接口：/rest/v1/authc/logout.action
	 */
	private String accountLogout = "/logout.action";

	/**
	 * 未授权Url
	 * 
	 */
	private String unauthorizedUrl = "/unauthorized.action";

	/**
	 * 登录Url
	 * 
	 */
	private String loginUrl = "/login";

	/**
	 * referer domain
	 */
	private String referers;

	/**
	 * 获取mode
	 * 
	 * @return
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * 设置mode
	 * 
	 * @param mode
	 */
	public void setMode(String mode)
	{
		this.mode = mode;
	}

	/**
	 * 获取environment
	 * 
	 * @return
	 */
	public String getEnvironment()
	{
		return environment;
	}

	/**
	 * 设置environment
	 * 
	 * @param environment
	 */
	public void setEnvironment(String environment)
	{
		this.environment = environment;
	}

	/**
	 * 获取accountLogin
	 * 
	 * @return
	 */
	public String getAccountLogin()
	{
		return accountLogin;
	}

	/**
	 * 设置accountLogin
	 * 
	 * @param accountLogin
	 */
	public void setAccountLogin(String accountLogin)
	{
		this.accountLogin = accountLogin;
	}

	/**
	 * 获取verifyCodeLogin
	 * 
	 * @return
	 */
	public String getVerifyCodeLogin()
	{
		return verifyCodeLogin;
	}

	/**
	 * 设置verifyCodeLogin
	 * 
	 * @param verifyCodeLogin
	 */
	public void setVerifyCodeLogin(String verifyCodeLogin)
	{
		this.verifyCodeLogin = verifyCodeLogin;
	}

	/**
	 * 获取weixinMPLogin
	 * 
	 * @return
	 */
	public String getWeixinMPLogin()
	{
		return weixinMPLogin;
	}

	/**
	 * 设置weixinMPLogin
	 * 
	 * @param weixinMPLogin
	 */
	public void setWeixinMPLogin(String weixinMPLogin)
	{
		this.weixinMPLogin = weixinMPLogin;
	}

	/**
	 * 获取weixinMiniLogin
	 * 
	 * @return
	 */
	public String getWeixinMiniLogin()
	{
		return weixinMiniLogin;
	}

	/**
	 * 设置weixinMiniLogin
	 * 
	 * @param weixinMiniLogin
	 */
	public void setWeixinMiniLogin(String weixinMiniLogin)
	{
		this.weixinMiniLogin = weixinMiniLogin;
	}

	/**
	 * 获取weixinOpenLogin
	 * 
	 * @return
	 */
	public String getWeixinOpenLogin()
	{
		return weixinOpenLogin;
	}

	/**
	 * 设置weixinOpenLogin
	 * 
	 * @param weixinOpenLogin
	 */
	public void setWeixinOpenLogin(String weixinOpenLogin)
	{
		this.weixinOpenLogin = weixinOpenLogin;
	}

	/**
	 * 获取appleLogin
	 * 
	 * @return
	 */
	public String getAppleLogin()
	{
		return appleLogin;
	}

	/**
	 * 设置appleLogin
	 * 
	 * @param appleLogin
	 */
	public void setAppleLogin(String appleLogin)
	{
		this.appleLogin = appleLogin;
	}

	/**
	 * 获取byteDanceMiniLogin
	 * 
	 * @return
	 */
	public String getByteDanceMiniLogin()
	{
		return byteDanceMiniLogin;
	}

	/**
	 * 设置byteDanceMiniLogin
	 * 
	 * @param byteDanceMiniLogin
	 */
	public void setByteDanceMiniLogin(String byteDanceMiniLogin)
	{
		this.byteDanceMiniLogin = byteDanceMiniLogin;
	}

	/**
	 * 获取accountLogout
	 * 
	 * @return
	 */
	public String getAccountLogout()
	{
		return accountLogout;
	}

	/**
	 * 设置accountLogout
	 * 
	 * @param accountLogout
	 */
	public void setAccountLogout(String accountLogout)
	{
		this.accountLogout = accountLogout;
	}

	/**
	 * 获取unauthorizedUrl
	 * 
	 * @return
	 */
	public String getUnauthorizedUrl()
	{
		return unauthorizedUrl;
	}

	/**
	 * 设置unauthorizedUrl
	 * 
	 * @param unauthorizedUrl
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl)
	{
		this.unauthorizedUrl = unauthorizedUrl;
	}

	/**
	 * 获取loginUrl
	 * 
	 * @return
	 */
	public String getLoginUrl()
	{
		return loginUrl;
	}

	/**
	 * 设置loginUrl
	 * 
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}

	/**
	 * 获取referers
	 * 
	 * @return
	 */
	public String getReferers()
	{
		return referers;
	}

	/**
	 * 设置referers
	 * 
	 * @param referers
	 */
	public void setReferers(String referers)
	{
		this.referers = referers;
	}
}
