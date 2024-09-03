package com.mizhousoft.security;

/**
 * 安全常量
 *
 * @version
 */
public interface SecurityConstants
{
	// User-Agent
	String HEADER_USER_AGENT = "User-Agent";

	// ACCEPT
	String HEADER_ACCEPT = "Accept";

	// Content-Type
	String HEADER_CONTENT_TYPE = "Content-Type";

	// 主机
	String HOST = "HOST";

	// 默认Session闲时超时时间
	int DEFAULT_SESSION_IDLE_TIMEOUT = 30 * 24 * 60;

	// 账号session
	String ACCOUNT_SESSION = "account.session";

	// Referer
	String HEADER_REFERER = "Referer";

	// csrf-token
	String COOKIE_CSRF_TOKEN = "csrf-token";

	// X-Csrf-Token
	String HEADER_X_CSRF_TOKEN = "X-Csrf-Token";

	// Cookie
	String HEADER_SET_COOKIE = "Set-Cookie";
}