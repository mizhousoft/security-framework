package com.mizhousoft.security.impl;

import java.util.Set;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.Authentication;

/**
 * 账号认证
 *
 * @version
 */
public class AccountAuthentication implements Authentication
{
	private static final long serialVersionUID = -3872720959509253551L;

	// 认证账号
	private final AccountDetails principal;

	// 访问IP地址
	private String host;

	/**
	 * 构造函数
	 *
	 * @param principal
	 */
	public AccountAuthentication(AccountDetails principal)
	{
		this.principal = principal;
	}

	/**
	 * 获取认证账号
	 * 
	 * @return
	 */
	@Override
	public AccountDetails getPrincipal()
	{
		return this.principal;
	}

	/**
	 * 获取账号ID
	 * 
	 * @return
	 */
	@Override
	public long getAccountId()
	{
		return this.principal.getAccountId();
	}

	/**
	 * 获取host
	 * 
	 * @return
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * 设置host
	 * 
	 * @param host
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * 获取认证账号名字
	 * 
	 * @return
	 */
	@Override
	public String getName()
	{
		return this.principal.getAccountName();
	}

	/**
	 * 获取授权
	 * 
	 * @return
	 */
	@Override
	public Set<String> getAuthorities()
	{
		return this.principal.getRoles();
	}
}
