package com.mizhousoft.security.web.authentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mizhousoft.commons.crypto.generator.RandomGenerator;
import com.mizhousoft.security.AccountDetails;
import com.mizhousoft.security.exception.AuthenticationException;
import com.mizhousoft.security.impl.AccountImpl;
import com.mizhousoft.security.request.AccountPasswordToken;
import com.mizhousoft.security.service.AccountPasswordAuthcService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AccountAuthcServiceImpl
 *
 * @version
 */
@Service
public class AccountPasswordAuthcServiceImpl implements AccountPasswordAuthcService
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountDetails authenticate(AccountPasswordToken token, String host, HttpServletRequest request) throws AuthenticationException
	{
		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setAccountId(22);
		accountImpl.setAccountName("demo");
		accountImpl.setCsrfToken(RandomGenerator.genHexString(32, true));

		Set<String> roles = new HashSet<>();
		accountImpl.setRoles(roles);

		roles.add("admin");

		return accountImpl;
	}
}
