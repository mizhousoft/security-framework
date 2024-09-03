package com.mizhousoft.security.util;

/**
 * 凭证工具类
 *
 * @version
 */
public abstract class PrincipalUtils
{
	public static String getPrincipalIndexName(long accountId)
	{
		return String.valueOf(accountId);
	}
}
