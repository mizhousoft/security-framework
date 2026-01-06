package com.mizhousoft.security.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.session.FindByIndexNameSessionRepository;

import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.SecurityConstants;
import com.mizhousoft.security.context.SecurityContext;
import com.mizhousoft.security.context.SecurityContextHolder;
import com.mizhousoft.security.context.SecurityContextImpl;
import com.mizhousoft.security.impl.AccountAuthentication;
import com.mizhousoft.security.service.PrincipalNameProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * SecurityContext持久化过滤器
 *
 * @version
 */
public class SecurityContextPersistenceFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(SecurityContextPersistenceFilter.class);

	private PrincipalNameProvider principalNameProvider;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		if (isRequestExcluded(request))
		{
			filterChain.doFilter(request, response);

			return;
		}

		HttpSession session = request.getSession();
		if (null == session)
		{
			accessUnauthorized(request, response);
			return;
		}

		Object object = session.getAttribute(SecurityConstants.ACCOUNT_SESSION);
		if (null == object || !(object instanceof AccountDetails))
		{
			accessUnauthorized(request, response);
			return;
		}

		AccountDetails accountDetails = (AccountDetails) object;

		String indexName = principalNameProvider.getPrincipalName(request, accountDetails);
		Object sessionIndexName = session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
		if (null == sessionIndexName || !indexName.equals(sessionIndexName))
		{
			LOG.error("Account session index name {} is invalid, account id is {}, indexName is {}.", sessionIndexName,
			        accountDetails.getAccountId(), indexName);
			redirectToLogin(request, response);
			return;
		}

		try
		{
			MDC.put("REQUEST_ACCOUNT_ID", String.valueOf(accountDetails.getAccountId()));

			SecurityContext securityContext = buildSecurityContext(accountDetails, request);
			SecurityContextHolder.setContext(securityContext);

			filterChain.doFilter(request, response);
		}
		finally
		{
			SecurityContextHolder.clearContext();
			MDC.clear();
		}
	}

	private SecurityContext buildSecurityContext(AccountDetails accountDetails, HttpServletRequest request)
	{
		String host = com.mizhousoft.commons.web.util.WebUtils.getFirstRemoteIPAddress(request);

		AccountAuthentication authentication = new AccountAuthentication(accountDetails);
		authentication.setHost(host);

		SecurityContextImpl securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authentication);

		return securityContext;
	}

	/**
	 * 设置principalNameProvider
	 * 
	 * @param principalNameProvider
	 */
	public void setPrincipalNameProvider(PrincipalNameProvider principalNameProvider)
	{
		this.principalNameProvider = principalNameProvider;
	}
}
