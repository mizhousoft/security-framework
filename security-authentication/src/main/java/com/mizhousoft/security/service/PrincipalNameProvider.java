package com.mizhousoft.security.service;

import com.mizhousoft.security.AccountDetails;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 主体名称提供者
 *
 * @version
 */
public interface PrincipalNameProvider
{
	/**
	 * 获取主体名称
	 * 
	 * @param request
	 * @param accountDetails
	 * @return
	 */
	String getPrincipalName(HttpServletRequest request, AccountDetails accountDetails);
}
