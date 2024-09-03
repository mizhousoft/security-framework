package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;

/**
 * 苹果请求
 *
 * @version
 */
public class AppleToken extends AuthenticationToken
{
	/**
	 * 用户标识
	 */
	private String appleUserId;

	/**
	 * Token
	 */
	private String identityToken;

	/**
	 * 授权码
	 */
	private String authCode;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		super.normalize();

		appleUserId = StringUtils.trimToNull(appleUserId);
		identityToken = StringUtils.trimToNull(identityToken);
		authCode = StringUtils.trimToNull(authCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		super.validate();
	}

	/**
	 * 获取appleUserId
	 * 
	 * @return
	 */
	public String getAppleUserId()
	{
		return appleUserId;
	}

	/**
	 * 设置appleUserId
	 * 
	 * @param appleUserId
	 */
	public void setAppleUserId(String appleUserId)
	{
		this.appleUserId = appleUserId;
	}

	/**
	 * 获取identityToken
	 * 
	 * @return
	 */
	public String getIdentityToken()
	{
		return identityToken;
	}

	/**
	 * 设置identityToken
	 * 
	 * @param identityToken
	 */
	public void setIdentityToken(String identityToken)
	{
		this.identityToken = identityToken;
	}

	/**
	 * 获取authCode
	 * 
	 * @return
	 */
	public String getAuthCode()
	{
		return authCode;
	}

	/**
	 * 设置authCode
	 * 
	 * @param authCode
	 */
	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}
}
