package com.mizhousoft.security.service;

import java.util.Set;

/**
 * 访问控制服务
 *
 * @version
 */
public interface AccessControlService
{
	/**
	 * 查询仅仅要认证的请求路径
	 * 
	 * @return
	 */
	Set<String> queryAuthcRequestPaths();

	/**
	 * 查询要认证和鉴权的请求路径
	 * 
	 * @return
	 */
	Set<String> queryAuthzRequestPaths();

	/**
	 * 获取匿名请求路径
	 * 
	 * @return
	 */
	Set<String> queryAnonRequestPaths();

	/**
	 * 获取静态精确路径
	 * 
	 * @return
	 */
	Set<String> queryStaticExactPaths();

	/**
	 * 获取静态表达式路径
	 * 
	 * @return
	 */
	Set<String> queryStaticPatternPaths();

	/**
	 * 根据请求路径获取角色
	 * 
	 * @param requestPath
	 * @return
	 */
	Set<String> getRolesByPath(String requestPath);
}
