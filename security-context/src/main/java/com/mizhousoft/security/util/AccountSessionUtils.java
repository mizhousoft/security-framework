package com.mizhousoft.security.util;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.SecurityConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 工具类
 *
 * @version
 */
public abstract class AccountSessionUtils
{
	public static AccountDetails getAccountDetails(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession(false);
		if (null == session)
		{
			return null;
		}

		Object object = session.getAttribute(SecurityConstants.ACCOUNT_SESSION);
		if (null != object && object instanceof AccountDetails accountDetails)
		{
			return accountDetails;
		}
		else
		{
			// 确保session不会存入数据库
			session.invalidate();
		}

		return null;
	}

	public static long getAccountId(HttpServletRequest httpRequest)
	{
		AccountDetails accountDetails = getAccountDetails(httpRequest);
		if (null != accountDetails)
		{
			return accountDetails.getAccountId();
		}

		return 0;
	}
}
