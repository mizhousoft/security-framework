package com.mizhousoft.security.context;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.Authentication;

/**
 * 工具类
 *
 * @version
 */
public abstract class PrincipalContextHolder
{
	public static <T extends AccountDetails> T getPrincipal(Class<T> clazz)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return clazz.cast(authentication.getPrincipal());
	}
}
