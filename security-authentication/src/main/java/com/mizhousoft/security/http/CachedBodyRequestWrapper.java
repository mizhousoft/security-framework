package com.mizhousoft.security.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 缓存BODY请求
 *
 */
public class CachedBodyRequestWrapper extends HttpServletRequestWrapper
{
	/**
	 * 缓存字节
	 */
	private final byte[] cachedBody;

	/**
	 * 构造函数
	 *
	 * @param request
	 * @throws IOException
	 */
	public CachedBodyRequestWrapper(HttpServletRequest request) throws IOException
	{
		super(request);

		cachedBody = request.getInputStream().readAllBytes();
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getBody()
	{
		return new String(cachedBody, StandardCharsets.UTF_8);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServletInputStream getInputStream()
	{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
		return new ServletInputStream()
		{
			@Override
			public boolean isFinished()
			{
				return byteArrayInputStream.available() == 0;
			}

			@Override
			public boolean isReady()
			{
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener)
			{
			}

			@Override
			public int read()
			{
				return byteArrayInputStream.read();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferedReader getReader()
	{
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
}