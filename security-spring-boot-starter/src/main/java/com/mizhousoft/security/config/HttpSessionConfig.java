package com.mizhousoft.security.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.util.pattern.PathPattern;

import com.mizhousoft.commons.lang.CollectionUtils;
import com.mizhousoft.security.filter.SecurityContextPersistenceFilter;
import com.mizhousoft.security.filter.authc.AccountPasswordAuthcFilter;
import com.mizhousoft.security.filter.authc.AppleAuthcFilter;
import com.mizhousoft.security.filter.authc.ByteDanceMiniAuthcFilter;
import com.mizhousoft.security.filter.authc.LogoutFilter;
import com.mizhousoft.security.filter.authc.VerificationCodeAuthcFilter;
import com.mizhousoft.security.filter.authc.WeixinMPAuthcFilter;
import com.mizhousoft.security.filter.authc.WeixinMiniAuthcFilter;
import com.mizhousoft.security.filter.authc.WeixinOpenAuthcFilter;
import com.mizhousoft.security.filter.authz.AccessAuthorizationFilter;
import com.mizhousoft.security.limiter.AuthFailureLimiter;
import com.mizhousoft.security.limiter.impl.IPAddrAuthFailureLimiter;
import com.mizhousoft.security.provider.DefaultPrincipalNameProvider;
import com.mizhousoft.security.service.AccessControlService;
import com.mizhousoft.security.service.AccountPasswordAuthcService;
import com.mizhousoft.security.service.AppleAuthcService;
import com.mizhousoft.security.service.ByteDanceMiniAuthcService;
import com.mizhousoft.security.service.PrincipalNameProvider;
import com.mizhousoft.security.service.VerificationCodeAuthcService;
import com.mizhousoft.security.service.WeixinMPAuthcService;
import com.mizhousoft.security.service.WeixinMiniAuthcService;
import com.mizhousoft.security.service.WeixinOpenAuthcService;
import com.mizhousoft.security.util.SecurityUtils;

import jakarta.servlet.Filter;

/**
 * HttpSessionConfig
 *
 * @version
 */
@Configuration
public class HttpSessionConfig
{
	@Autowired
	private SecurityProperties securityProperties;

	@Autowired(required = false)
	private PrincipalNameProvider principalNameProvider = new DefaultPrincipalNameProvider();

	private AuthFailureLimiter ipAddrFailureLimiter = new IPAddrAuthFailureLimiter(30);

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver()
	{
		if ("cookie".equals(securityProperties.getMode()))
		{
			String cookieName = "JSESSIONID";
			boolean isProdMode = !"dev".equals(securityProperties.getEnvironment());

			DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
			cookieSerializer.setCookieName(cookieName);
			cookieSerializer.setUseHttpOnlyCookie(true);
			cookieSerializer.setUseSecureCookie(isProdMode);
			cookieSerializer.setUseBase64Encoding(true);

			CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
			cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
			return cookieHttpSessionIdResolver;
		}
		else
		{
			return HeaderHttpSessionIdResolver.xAuthToken();
		}
	}

	@Bean
	public FilterRegistrationBean<Filter> getAccessAuthorizationFilter(AccessControlService accessControlService)
	{
		Set<String> authzPaths = accessControlService.queryAuthzRequestPaths();
		if (authzPaths.isEmpty())
		{
			throw new RuntimeException("Authz request paths is empty.");
		}

		AccessAuthorizationFilter filter = new AccessAuthorizationFilter();
		filter.setAccessControlService(accessControlService);
		filter.setLoginUrl(securityProperties.getLoginUrl());
		filter.setUnauthorizedUrl(securityProperties.getUnauthorizedUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(authzPaths.toArray(new String[0]));
		registration.setName("authzFilter");
		registration.setOrder(-1100);

		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> getLogoutFilter()
	{
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();

		LogoutFilter filter = new LogoutFilter();
		filter.setLoginUrl(securityProperties.getLoginUrl());

		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getAccountLogout());
		registration.setName("logoutFilter");
		registration.setOrder(-1200);

		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> getSecurityContextPersistenceFilter(AccessControlService accessControlService)
	{
		Set<String> anonPaths = accessControlService.queryAnonRequestPaths();
		Set<String> staticExactPaths = accessControlService.queryStaticExactPaths();

		Set<String> excludeExactPaths = new HashSet<>(anonPaths);
		excludeExactPaths.addAll(staticExactPaths);

		Set<String> staticPatternPaths = accessControlService.queryStaticPatternPaths();
		List<PathPattern> excludedPatterns = SecurityUtils.convertPathPattern(staticPatternPaths);

		CollectionUtils.addIgnoreNull(excludeExactPaths, securityProperties.getLoginUrl());
		CollectionUtils.addIgnoreNull(excludeExactPaths, securityProperties.getUnauthorizedUrl());

		SecurityContextPersistenceFilter filter = new SecurityContextPersistenceFilter();
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());
		filter.setExcludeExactPaths(excludeExactPaths);
		filter.setExcludedPatterns(excludedPatterns);

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns("/*");
		registration.setName("securityContextFilter");
		registration.setOrder(-1500);

		return registration;
	}

	@Bean
	@ConditionalOnBean(AccountPasswordAuthcService.class)
	public FilterRegistrationBean<Filter> getAccountPasswordAuthenticationFilter(AccountPasswordAuthcService accountAuthcService)
	{
		AccountPasswordAuthcFilter filter = new AccountPasswordAuthcFilter();
		filter.setAccountAuthcService(accountAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getAccountLogin());
		registration.setName("accountAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(VerificationCodeAuthcService.class)
	public FilterRegistrationBean<Filter> getVerificationCodeAuthcFilter(VerificationCodeAuthcService verificationCodeAuthcService)
	{
		VerificationCodeAuthcFilter filter = new VerificationCodeAuthcFilter();
		filter.setVerificationCodeAuthcService(verificationCodeAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getVerifyCodeLogin());
		registration.setName("verifyCodeAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(WeixinMPAuthcService.class)
	public FilterRegistrationBean<Filter> getWeixinMPAuthcFilter(WeixinMPAuthcService weixinMPAuthcService)
	{
		WeixinMPAuthcFilter filter = new WeixinMPAuthcFilter();
		filter.setWeixinMPAuthcService(weixinMPAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getWeixinMPLogin());
		registration.setName("weixinMPAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(WeixinMiniAuthcService.class)
	public FilterRegistrationBean<Filter> getWeixinMiniAuthcFilter(WeixinMiniAuthcService weixinMiniAuthcService)
	{
		WeixinMiniAuthcFilter filter = new WeixinMiniAuthcFilter();
		filter.setWeixinMiniAuthcService(weixinMiniAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getWeixinMiniLogin());
		registration.setName("weixinMiniAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(WeixinOpenAuthcService.class)
	public FilterRegistrationBean<Filter> getWeixinOpenAuthcFilter(WeixinOpenAuthcService weixinOpenAuthcService)
	{
		WeixinOpenAuthcFilter filter = new WeixinOpenAuthcFilter();
		filter.setWeixinOpenAuthcService(weixinOpenAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getWeixinOpenLogin());
		registration.setName("weixinOpenAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(AppleAuthcService.class)
	public FilterRegistrationBean<Filter> getAppleAuthcFilter(AppleAuthcService appleAuthcService)
	{
		AppleAuthcFilter filter = new AppleAuthcFilter();
		filter.setAppleAuthcService(appleAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getAppleLogin());
		registration.setName("appleAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}

	@Bean
	@ConditionalOnBean(ByteDanceMiniAuthcService.class)
	public FilterRegistrationBean<Filter> getByteDanceMiniAuthcFilter(ByteDanceMiniAuthcService byteDanceMiniAuthcService)
	{
		ByteDanceMiniAuthcFilter filter = new ByteDanceMiniAuthcFilter();
		filter.setByteDanceMiniAuthcService(byteDanceMiniAuthcService);
		filter.setAuthFailureLimiter(ipAddrFailureLimiter);
		filter.setPrincipalNameProvider(principalNameProvider);
		filter.setLoginUrl(securityProperties.getLoginUrl());

		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(filter);
		registration.addUrlPatterns(securityProperties.getByteDanceMiniLogin());
		registration.setName("byteDanceMiniAuthcFilter");
		registration.setOrder(-2000);

		return registration;
	}
}
