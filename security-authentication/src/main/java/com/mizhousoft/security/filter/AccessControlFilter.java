package com.mizhousoft.security.filter;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;

import com.mizhousoft.commons.web.util.CookieUtils;
import com.mizhousoft.commons.web.util.WebUtils;
import com.mizhousoft.security.SecurityConstants;
import com.mizhousoft.security.util.ResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 访问控制过滤器
 *
 * @version
 */
public abstract class AccessControlFilter extends OncePerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AccessControlFilter.class);

	private static final String GET_METHOD = "get";

	/**
	 * 登录URL
	 */
	private String loginUrl;

	/**
	 * 未授权URL
	 */
	private String unauthorizedUrl;

	/**
	 * 排除的路径
	 */
	private Set<String> excludeExactPaths = Collections.emptySet();

	/**
	 * 要排除的路径匹配表达式
	 */
	private List<PathPattern> excludedPatterns = Collections.emptyList();

	/**
	 * 是否请求排除
	 * 
	 * @param requestPath
	 * @return
	 */
	protected boolean isRequestExcluded(HttpServletRequest request)
	{
		String requestPath = WebUtils.getPathWithinApplication(request);

		if (excludeExactPaths.contains(requestPath)
		        || excludedPatterns.stream().anyMatch(pattern -> pattern.matches(PathContainer.parsePath(requestPath))))
		{
			return true;
		}

		return false;
	}

	/**
	 * 是否JSON请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isJSONRequest(HttpServletRequest request)
	{
		String accept = request.getHeader(SecurityConstants.HEADER_ACCEPT);
		if (null != accept && accept.contains(MediaType.APPLICATION_JSON_VALUE))
		{
			return true;
		}

		String contentType = request.getHeader(SecurityConstants.HEADER_CONTENT_TYPE);
		if (null != contentType && contentType.contains(MediaType.APPLICATION_JSON_VALUE))
		{
			return true;
		}

		return false;
	}

	/**
	 * 访问未授权
	 * 
	 * @param request
	 * @param response
	 */
	protected void accessUnauthorized(HttpServletRequest request, HttpServletResponse response)
	{
		String path = WebUtils.getPathWithinApplication(request);
		LOG.warn("Account does not have authentication and does not allow access, path is {}, class is {}", path,
		        this.getClass().getSimpleName());

		redirectToLogin(request, response);
	}

	/**
	 * 访问未授权
	 * 
	 * @param request
	 * @param response
	 */
	protected void redirectToLogin(HttpServletRequest request, HttpServletResponse response)
	{
		destroySession(request, response, true);

		try
		{
			String loginUrl = getLoginUrl();
			String location = buildLocationUrl(request, loginUrl);

			if (isJSONRequest(request))
			{
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				String respBody = ResponseBuilder.buildUnauthorized(location);
				response.getWriter().write(respBody);
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_SEE_OTHER);
				response.setHeader("Location", location);
			}
		}
		catch (Throwable e)
		{
			LOG.error("Redirect to login failed.", e);
		}
	}

	/**
	 * 访问被拒绝
	 * 
	 * @param request
	 * @param response
	 */
	protected void accessDenied(HttpServletRequest request, HttpServletResponse response)
	{
		String path = WebUtils.getPathWithinApplication(request);
		LOG.warn("Account does not have permission to access, path is {}, class is {}", path, this.getClass().getSimpleName());

		try
		{
			String unauthorizedUrl = getUnauthorizedUrl();
			String location = buildLocationUrl(request, unauthorizedUrl);

			if (isJSONRequest(request))
			{
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				String respBody = ResponseBuilder.buildForbidden();
				response.getWriter().write(respBody);
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_SEE_OTHER);
				response.setHeader("Location", location);
			}
		}
		catch (Throwable e)
		{
			LOG.error("Write forbidden response to client failed.", e);
		}
	}

	/**
	 * 销毁session
	 * 
	 * @param request
	 */
	protected void destroySession(HttpServletRequest request, HttpServletResponse response, boolean force)
	{
		try
		{
			CookieUtils.removeCookie(request, response, SecurityConstants.COOKIE_CSRF_TOKEN);

			HttpSession session = request.getSession(force);
			if (null != session)
			{
				session.invalidate();
			}
		}
		catch (Throwable e)
		{
			LOG.error("Destroy session failed.", e);
		}
	}

	/**
	 * 构建Location URL
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	protected String buildLocationUrl(HttpServletRequest request, String url)
	{
		if (null == url)
		{
			return null;
		}

		StringBuilder targetUrl = new StringBuilder();
		if (url.startsWith("/"))
		{
			targetUrl.append(WebUtils.getContextPath(request));
		}
		else
		{
			LOG.warn("Url is {}.", url);
		}

		targetUrl.append(url);

		return targetUrl.toString();
	}

	/**
	 * 是否GET请求
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isGetMethod(HttpServletRequest request)
	{
		String httpMethod = request.getMethod();

		return (GET_METHOD.equalsIgnoreCase(httpMethod));
	}

	/**
	 * 获取loginUrl
	 * 
	 * @return
	 */
	public String getLoginUrl()
	{
		return loginUrl;
	}

	/**
	 * 设置loginUrl
	 * 
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}

	/**
	 * 获取unauthorizedUrl
	 * 
	 * @return
	 */
	public String getUnauthorizedUrl()
	{
		return unauthorizedUrl;
	}

	/**
	 * 设置unauthorizedUrl
	 * 
	 * @param unauthorizedUrl
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl)
	{
		this.unauthorizedUrl = unauthorizedUrl;
	}

	/**
	 * 设置excludeExactPaths
	 * 
	 * @param excludeExactPaths
	 */
	public void setExcludeExactPaths(Set<String> excludeExactPaths)
	{
		this.excludeExactPaths = excludeExactPaths;
	}

	/**
	 * 设置excludedPatterns
	 * 
	 * @param excludedPatterns
	 */
	public void setExcludedPatterns(List<PathPattern> excludedPatterns)
	{
		this.excludedPatterns = excludedPatterns;
	}
}
