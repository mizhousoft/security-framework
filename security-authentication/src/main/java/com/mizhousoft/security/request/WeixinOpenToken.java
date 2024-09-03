package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 微信请求
 *
 * @version
 */
public class WeixinOpenToken extends AuthenticationToken
{
	// 验证码
	private String code;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		super.normalize();

		code = StringUtils.trimToNull(code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		super.validate();

		Assert.notNull("code", code, "security.authentication.login.failed");
		Assert.size("code", code, 1, 64, "security.authentication.login.failed");
	}

	/**
	 * 获取code
	 * 
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * 设置code
	 * 
	 * @param code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
}
