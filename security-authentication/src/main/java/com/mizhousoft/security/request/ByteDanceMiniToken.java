package com.mizhousoft.security.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.Validator;
import com.mizhousoft.commons.web.util.Assert;

/**
 * 字节请求
 *
 * @version
 */
public class ByteDanceMiniToken extends AuthenticationToken implements Validator
{
	/**
	 * 验证码
	 */
	private String code;

	/**
	 * 加密数据
	 */
	private String encryptedData;

	/**
	 * iv向量
	 */
	private String iv;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		super.normalize();

		code = StringUtils.trimToNull(code);
		encryptedData = StringUtils.trimToNull(encryptedData);
		iv = StringUtils.trimToNull(iv);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() throws AssertionException
	{
		Assert.notNull("code", code, "security.authentication.login.failed");
		Assert.size("code", code, 1, 200, "security.authentication.login.failed");
	}

	/**
	 * 获取code
	 * 
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * 设置code
	 * 
	 * @param code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * 获取encryptedData
	 * 
	 * @return
	 */
	public String getEncryptedData()
	{
		return encryptedData;
	}

	/**
	 * 设置encryptedData
	 * 
	 * @param encryptedData
	 */
	public void setEncryptedData(String encryptedData)
	{
		this.encryptedData = encryptedData;
	}

	/**
	 * 获取iv
	 * 
	 * @return
	 */
	public String getIv()
	{
		return iv;
	}

	/**
	 * 设置iv
	 * 
	 * @param iv
	 */
	public void setIv(String iv)
	{
		this.iv = iv;
	}
}
