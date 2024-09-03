package com.mizhousoft.security.impl;

import java.util.Collections;
import java.util.Set;

import com.mizhousoft.security.AccountDetails;

/**
 * 账号
 *
 * @version
 */
public class AccountImpl implements AccountDetails
{
	private static final long serialVersionUID = -8217716359571352682L;

	// 账号ID
	private long accountId;

	// 账号名
	private String accountName;

	// 登录方式
	private String loginMethod;

	// 授权角色
	private Set<String> roles;

	// csrf-token
	private String csrfToken;

	/**
	 * 获取accountId
	 * 
	 * @return
	 */
	@Override
	public long getAccountId()
	{
		return accountId;
	}

	/**
	 * 设置accountId
	 * 
	 * @param accountId
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}

	/**
	 * 获取账号名
	 * 
	 * @return
	 */
	@Override
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * 设置accountName
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * 获取loginMethod
	 * 
	 * @return
	 */
	@Override
	public String getLoginMethod()
	{
		return loginMethod;
	}

	/**
	 * 设置loginMethod
	 * 
	 * @param loginMethod
	 */
	public void setLoginMethod(String loginMethod)
	{
		this.loginMethod = loginMethod;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Set<String> getRoles()
	{
		return roles;
	}

	/**
	 * 设置roles
	 * 
	 * @param roles
	 */
	public void setRoles(Set<String> roles)
	{
		if (roles == null)
		{
			roles = Collections.emptySet();
		}

		this.roles = Collections.unmodifiableSet(roles);
	}

	/**
	 * 获取csrfToken
	 * 
	 * @return
	 */
	@Override
	public String getCsrfToken()
	{
		return csrfToken;
	}

	/**
	 * 设置csrfToken
	 * 
	 * @param csrfToken
	 */
	public void setCsrfToken(String csrfToken)
	{
		this.csrfToken = csrfToken;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		return result;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}
		AccountImpl other = (AccountImpl) obj;
		if (accountId != other.accountId)
		{
			return false;
		}
		if (accountName == null)
		{
			if (other.accountName != null)
			{
				return false;
			}
		}
		else if (!accountName.equals(other.accountName))
		{
			return false;
		}

		return true;
	}
}
