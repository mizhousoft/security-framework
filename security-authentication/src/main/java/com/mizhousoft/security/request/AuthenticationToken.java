package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Normalizer;
import com.mizhousoft.commons.web.Validator;

/**
 * 认证请求
 *
 * @version
 */
public abstract class AuthenticationToken implements Normalizer, Validator
{
	/**
	 * 请求扩展数据
	 */
	protected String extra;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		extra = StringUtils.trimToNull(extra);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{

	}

	/**
	 * 获取extra
	 * 
	 * @return
	 */
	public String getExtra()
	{
		return extra;
	}

	/**
	 * 设置extra
	 * 
	 * @param extra
	 */
	public void setExtra(String extra)
	{
		this.extra = extra;
	}
}
