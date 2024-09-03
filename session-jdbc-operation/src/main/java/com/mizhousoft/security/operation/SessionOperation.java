package com.mizhousoft.security.operation;

/**
 * Session操作
 *
 * @version
 */
public interface SessionOperation
{
	/**
	 * 注销
	 * 
	 * @param principalName
	 */
	void logout(String principalName);

	/**
	 * 异步注销
	 * 
	 * @param principalName
	 */
	void asyncLogout(String principalName);

	/**
	 * Session是否过期
	 * 
	 * @param principalName
	 * @return
	 */
	boolean isSessionExpired(String principalName);
}
