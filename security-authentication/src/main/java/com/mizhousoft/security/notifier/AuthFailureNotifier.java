package com.mizhousoft.security.notifier;

import java.time.LocalDateTime;

/**
 * 认证失败通知器
 *
 * @version
 */
public interface AuthFailureNotifier
{
	/**
	 * 通知
	 * 
	 * @param host
	 * @param failCount
	 * @param failTime
	 */
	void notify(String host, int failCount, LocalDateTime failTime);
}
