package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 验证码请求
 *
 * @version
 */
public class VerificationCodeToken extends AuthenticationToken
{
	// 手机号
	private String phoneNumber;

	// 验证码
	private String code;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		super.normalize();

		phoneNumber = StringUtils.trimToNull(phoneNumber);
		code = StringUtils.trimToNull(code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		super.validate();

		Assert.notNull("phoneNumber", phoneNumber, "security.authentication.login.failed");
		Assert.notNull("code", code, "security.authentication.login.failed");
	}

	/**
	 * 获取phoneNumber
	 * 
	 * @return
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * 设置phoneNumber
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
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
