package com.mizhousoft.security;

import java.io.Serializable;
import java.util.Set;

/**
 * 账号
 *
 * @version
 */
public interface AccountDetails extends Serializable
{
	/**
	 * 获取授权角色
	 * 
	 * @return
	 */
	Set<String> getRoles();

	/**
	 * 获取账号ID
	 * 
	 * @return
	 */
	long getAccountId();

	/**
	 * 获取账号名
	 * 
	 * @return
	 */
	String getAccountName();

	/**
	 * 获取登录方式
	 * 
	 * @return
	 */
	String getLoginMethod();

	/**
	 * 获取csrf-token
	 * 
	 * @return
	 */
	String getCsrfToken();
}
