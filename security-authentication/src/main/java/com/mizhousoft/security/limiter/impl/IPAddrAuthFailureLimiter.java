package com.mizhousoft.security.limiter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.security.exception.AccountLockedException;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.limiter.FailureCounter;
import com.mizhousoft.security.notifier.AuthFailureNotifier;

/**
 * 认证失败限制器
 *
 * @version
 */
public class IPAddrAuthFailureLimiter extends AbstractAuthFailureLimiter
{
	private static final Logger LOG = LoggerFactory.getLogger(IPAddrAuthFailureLimiter.class);

	/**
	 * 认证失败通知器
	 */
	private AuthFailureNotifier authFailureNotifier;

	/**
	 * 构造函数
	 *
	 * @param limitNumber
	 * @param authFailureNotifier
	 */
	public IPAddrAuthFailureLimiter(int limitNumber, AuthFailureNotifier authFailureNotifier)
	{
		super(limitNumber);

		this.authFailureNotifier = authFailureNotifier;
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
				notify(entity, faildCounter);

				throw new AccountLockedException("intl.security.authentication.ip.locked.error", entity + " is locked.");
			}
		}
	}

	/**
	 * 通知
	 * 
	 * @param host
	 * @param faildCounter
	 */
	private void notify(String host, FailureCounter faildCounter)
	{
		if (null == authFailureNotifier)
		{
			return;
		}

		try
		{
			authFailureNotifier.notify(host, faildCounter.getFailedCount(), faildCounter.getFailedTime());
		}
		catch (Throwable e)
		{
			LOG.error("Notify auth failed.", e);
		}
	}
}
