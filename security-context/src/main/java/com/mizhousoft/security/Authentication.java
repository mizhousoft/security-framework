package com.mizhousoft.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

/**
 * 认证信息
 *
 * @version
 */
public interface Authentication extends Principal, Serializable
{
	/**
	 * 获取授权
	 * 
	 * @return
	 */
	Set<String> getAuthorities();

	/**
	 * 获取认证账号
	 * 
	 * @return
	 */
	AccountDetails getPrincipal();

	/**
	 * 获取账号ID
	 * 
	 * @return
	 */
	long getAccountId();

	/**
	 * 获取访问的IP地址
	 * 
	 * @return
	 */
	String getHost();
}
