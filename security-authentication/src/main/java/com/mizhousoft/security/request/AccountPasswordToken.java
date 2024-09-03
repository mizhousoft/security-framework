package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 账号请求
 *
 * @version
 */
public class AccountPasswordToken extends AuthenticationToken
{
	// 账号
	private String account;

	// 密码
	private String password;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		super.normalize();

		account = StringUtils.trimToNull(account);
		password = StringUtils.trimToNull(password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		super.validate();

		Assert.notNull("account", account, "security.authentication.login.failed");
		Assert.notNull("password", password, "security.authentication.login.failed");
	}

	/**
	 * 获取account
	 * 
	 * @return
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * 设置account
	 * 
	 * @param account
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}

	/**
	 * 获取password
	 * 
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * 设置password
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
}
