package com.mizhousoft.security.provider;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.service.PrincipalNameProvider;
import com.mizhousoft.security.util.PrincipalUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 默认的主体名称提供者
 *
 * @version
 */
public class DefaultPrincipalNameProvider implements PrincipalNameProvider
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrincipalName(HttpServletRequest request, AccountDetails accountDetails)
	{
		String indexName = PrincipalUtils.getPrincipalIndexName(accountDetails.getAccountId());

		return indexName;
	}
}
