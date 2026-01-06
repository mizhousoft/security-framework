package com.mizhousoft.security.web.authentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mizhousoft.security.service.AccessControlService;

/**
 * AccessControlServiceImpl
 *
 * @version
 */
@Service
public class AccessControlServiceImpl implements AccessControlService
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryAuthcRequestPaths()
	{
		Set<String> paths = new HashSet<>(10);
		paths.add("/test.action");
		paths.add("/list.action");
		paths.add("/disable.action");

		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryAuthzRequestPaths()
	{
		Set<String> paths = new HashSet<>(10);
		paths.add("/user.action");

		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryAnonRequestPaths()
	{
		Set<String> paths = new HashSet<>(10);
		paths.add("/big.action");

		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryStaticExactPaths()
	{
		Set<String> paths = new HashSet<>(10);

		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> queryStaticPatternPaths()
	{
		Set<String> paths = new HashSet<>(10);

		return paths;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getRolesByPath(String requestPath)
	{
		Set<String> roles = new HashSet<>(1);
		roles.add("admin");

		return roles;
	}

}
