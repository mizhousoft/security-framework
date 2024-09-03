package com.mizhousoft.security.limiter.impl;

import com.mizhousoft.security.exception.AccountLockedException;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.limiter.FailureCounter;

/**
 * 认证失败限制器
 *
 * @version
 */
public class AccountAuthFailureLimiter extends AbstractAuthFailureLimiter
{
	/**
	 * 构造函数
	 *
	 * @param limitNumber
	 */
	public AccountAuthFailureLimiter(int limitNumber)
	{
		super(limitNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tryAcquire(String entity) throws AuthenticationException
	{
		FailureCounter faildCounter = counterCache.getIfPresent(entity);
		if (null != faildCounter)
		{
			int value = faildCounter.getFailedCount();
			if (value >= limitNumber)
			{
				throw new AccountLockedException("security.authentication.account.locked.error", entity + " is locked.");
			}
		}
	}
}
