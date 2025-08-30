package com.mizhousoft.security.operation.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.mizhousoft.security.operation.SessionOperation;

/**
 * Session操作
 *
 * @version
 */
public class SessionJdbcOperation implements SessionOperation
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionJdbcOperation.class);

	private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logout(String principalName)
	{
		try
		{
			Map<String, ? extends Session> sessionMap = this.sessionRepository.findByPrincipalName(principalName);
			if (!sessionMap.isEmpty())
			{
				Iterator<String> iter = sessionMap.keySet().iterator();
				while (iter.hasNext())
				{
					String sessionId = iter.next();
					sessionRepository.deleteById(sessionId);

					LOG.warn("Logout principal successfully, principal id is {}, session id is {}.", principalName, sessionId);
				}
			}
		}
		catch (Throwable e)
		{
			LOG.error("Logout principal {} failed.", principalName, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Async
	@Override
	public void asyncLogout(String principalName)
	{
		logout(principalName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSessionExpired(String principalName)
	{
		Map<String, ? extends Session> sessionMap = this.sessionRepository.findByPrincipalName(principalName);
		if (!sessionMap.isEmpty())
		{
			Collection<? extends Session> sessions = sessionMap.values();
			for (Session session : sessions)
			{
				if (!session.isExpired())
				{
					return true;
				}
			}
		}

		return false;
	}

	public void initialize(DataSource dataSource, PlatformTransactionManager transactionManager, String tableName)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.afterPropertiesSet();

		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

		JdbcIndexedSessionRepository sessionRepository = new JdbcIndexedSessionRepository(jdbcTemplate, transactionTemplate);
		sessionRepository.setTableName(tableName);

		this.sessionRepository = sessionRepository;

		LOG.info("Init {} spring session repository successfully.", tableName);
	}

}
