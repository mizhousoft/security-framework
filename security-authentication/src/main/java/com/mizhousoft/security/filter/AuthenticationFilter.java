package com.mizhousoft.security.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.session.FindByIndexNameSessionRepository;

import com.mizhousoft.commons.json.JSONException;
import com.mizhousoft.commons.json.JSONUtils;
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;
import com.mizhousoft.commons.web.util.CookieBuilder;
import com.mizhousoft.commons.web.util.WebUtils;
import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.SecurityConstants;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.exception.BadCredentialsException;
import com.mizhousoft.security.exception.UnknownAccountException;
import com.mizhousoft.security.limiter.AuthFailureLimiter;
import com.mizhousoft.security.request.AuthenticationToken;
import com.mizhousoft.security.service.PrincipalNameProvider;
import com.mizhousoft.security.util.ResponseBuilder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 认证过滤器
 *
 * @version
 */
public abstract class AuthenticationFilter extends AccessControlFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

	protected PrincipalNameProvider principalNameProvider;

	protected AuthFailureLimiter authFailureLimiter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException
	{
		try
		{
			if (isGetMethod(request))
			{
				loginFailure(request, response,
				        new BadCredentialsException("security.authentication.login.failed", "Request method is get."));
				return;
			}

			AccountDetails accountDetails = authenticate(request, response);

			loginSuccess(accountDetails, request, response);

			filterChain.doFilter(request, response);
		}
		catch (Throwable e)
		{
			loginFailure(request, response, e);
		}
	}

	private AccountDetails authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
	{
		String host = com.mizhousoft.commons.web.util.WebUtils.getFirstRemoteIPAddress(request);

		try
		{
			authFailureLimiter.tryAcquire(host);

			AccountDetails accountDetails = doAuthenticate(request, response, host);

			authFailureLimiter.clear(host);

			return accountDetails;
		}
		catch (com.mizhousoft.security.exception.AuthenticationException e)
		{
			authFailureLimiter.authcFailure(host);

			LOG.error("Account authenticate failed, message: {}", e.getMessage());

			throw e;
		}
		catch (Throwable e)
		{
			LOG.error("Account authenticate failed.", e);

			throw new UnknownAccountException("security.authentication.login.failed", e.getMessage(), e);
		}
	}

	protected abstract AccountDetails doAuthenticate(HttpServletRequest request, HttpServletResponse response, String host)
	        throws AuthenticationException;

	protected <T extends AuthenticationToken> T createToken(HttpServletRequest request, HttpServletResponse response, Class<T> clazz)
	        throws UnknownAccountException
	{
		try
		{
			if (!isJSONRequest(request))
			{
				throw new UnknownAccountException("security.authentication.login.failed",
				        "Request header accept does not application/json.");
			}

			String requestBody = com.mizhousoft.commons.web.util.WebUtils.getRequestBody(request);
			if (StringUtils.isBlank(requestBody))
			{
				throw new UnknownAccountException("security.authentication.login.failed", "Request body is null.");
			}

			T token = JSONUtils.parse(requestBody, clazz);
			token.normalize();
			token.validate();

			return token;
		}
		catch (IOException | JSONException e)
		{
			throw new UnknownAccountException("security.authentication.login.failed", "Parse request body to AuthenticationToken failed.");
		}
		catch (AssertionException e)
		{
			throw new UnknownAccountException(e.getErrorCode(), e.getCodeParams(), e.getMessage());
		}
	}

	/**
	 * 登录成功
	 * 
	 * @param accountDetails
	 * @param request
	 * @param response
	 */
	private void loginSuccess(AccountDetails accountDetails, HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Account login successfully, id is {}, name is {}.", accountDetails.getAccountId(), accountDetails.getAccountName());

		destroySession(request, response, true);

		HttpSession session = request.getSession(true);

		session.setAttribute(SecurityConstants.ACCOUNT_SESSION, accountDetails);

		String indexName = principalNameProvider.getPrincipalName(request, accountDetails);
		session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, indexName);

		if (null != accountDetails.getCsrfToken())
		{
			String path = WebUtils.getContextPath(request);
			String cookie = CookieBuilder.build(SecurityConstants.COOKIE_CSRF_TOKEN, accountDetails.getCsrfToken(), null, path, true, false);
			response.addHeader(SecurityConstants.HEADER_SET_COOKIE, cookie);
		}
	}

	/**
	 * 登录失败
	 * 
	 * @param request
	 * @param response
	 * @param cause
	 */
	private void loginFailure(HttpServletRequest request, HttpServletResponse response, Throwable cause)
	{
		String error = null;
		String code = null;
		if (null != cause && cause instanceof AuthenticationException)
		{
			String errorCode = ((AuthenticationException) cause).getErrorCode();
			String[] params = ((AuthenticationException) cause).getCodeParams();

			error = I18nUtils.getMessage(errorCode, params);

			code = ((AuthenticationException) cause).getCode();
		}

		destroySession(request, response, true);

		try
		{
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			String respBody = ResponseBuilder.buildAuthcFailed(error, code);
			response.getWriter().write(respBody);
		}
		catch (Throwable e)
		{
			LOG.error("Write login failure response to client failed.", e);
		}
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

	/**
	 * 设置authFailureLimiter
	 * 
	 * @param authFailureLimiter
	 */
	public void setAuthFailureLimiter(AuthFailureLimiter authFailureLimiter)
	{
		this.authFailureLimiter = authFailureLimiter;
	}
}
