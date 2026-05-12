package com.mizhousoft.security.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mizhousoft.commons.crypto.CryptoException;
import com.mizhousoft.commons.crypto.digest.HmacSHA256Digest;
import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.commons.lang.HexUtils;
import com.mizhousoft.commons.web.AssertionException;
import com.mizhousoft.commons.web.util.Assert;
import com.mizhousoft.commons.web.util.WebUtils;
import com.mizhousoft.security.http.CachedBodyRequestWrapper;
import com.mizhousoft.security.util.ResponseBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 签名认证拦截器
 *
 */
public abstract class HMACSignInterceptor implements HandlerInterceptor
{
	private static final Logger LOG = LoggerFactory.getLogger(HMACSignInterceptor.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		try
		{
			String timestamp = request.getHeader("X-Timestamp");
			Assert.notNull(timestamp, "Missing X-Timestamp header");

			String nonce = request.getHeader("X-Nonce");
			Assert.notNull(nonce, "Missing X-Nonce header");

			String signature = request.getHeader("X-Signature");
			Assert.notNull(signature, "Missing X-Signature header");

			// 5分钟过期
			long requestTime = Long.parseLong(timestamp);
			if (Math.abs(System.currentTimeMillis() - requestTime) > 5 * 60 * 1000)
			{
				throw new AssertionException("Request has expired");
			}

			TreeMap<String, String> requestParamMap = new TreeMap<>();
			request.getParameterMap().forEach((k, v) -> {
				if (v != null && v.length > 0)
					requestParamMap.put(k, v[0]);
			});

			String requestBody = "";
			if ("post".equalsIgnoreCase(request.getMethod()) && request instanceof CachedBodyRequestWrapper wrapper)
			{
				String body = wrapper.getBody();
				if (body != null && !body.isEmpty())
				{
					requestBody = body;
				}
			}

			String signValue = sign(timestamp, nonce, request.getRequestURI(), requestParamMap, requestBody);
			if (!signValue.equals(signature))
			{
				throw new AssertionException("Signature wrong");
			}

			return true;
		}
		catch (AssertionException e)
		{
			LOG.error("Signature validate failed.", e);
			accessDenied(request, response, e.getMessage());
		}
		catch (Throwable e)
		{
			LOG.error("Signature validate failed.", e);
			accessDenied(request, response, "Signature validate failed.");
		}

		return false;
	}

	/**
	 * 获取访问密钥
	 * 
	 * @return
	 */
	protected abstract String getAccessSecret();

	/**
	 * 签名
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param path
	 * @param requestParamMap
	 * @param requestBody
	 * @return
	 * @throws CryptoException
	 */
	protected String sign(String timestamp, String nonce, String path, TreeMap<String, String> requestParamMap, String requestBody)
	        throws CryptoException
	{
		String paramsStr = requestParamMap.entrySet()
		        .stream()
		        .filter(entry -> entry.getValue() != null)
		        .sorted(Map.Entry.comparingByKey())
		        .map(entry -> entry.getKey() + "=" + entry.getValue())
		        .collect(Collectors.joining("&"));

		String secret = getAccessSecret();

		String plainText = String.join("_", timestamp, nonce, path, paramsStr, requestBody);

		byte[] bytes = HmacSHA256Digest.hash(secret.getBytes(CharEncoding.UTF8), plainText.getBytes(StandardCharsets.UTF_8));

		return HexUtils.encodeHexString(bytes, true);
	}

	/**
	 * 访问被拒绝
	 * 
	 * @param request
	 * @param response
	 * @param error
	 */
	private void accessDenied(HttpServletRequest request, HttpServletResponse response, String error)
	{
		String path = WebUtils.getPathWithinApplication(request);

		LOG.warn("Request denied, path is {}, class is {}", path, this.getClass().getSimpleName());

		try
		{
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
			response.setCharacterEncoding(CharEncoding.UTF8);

			String respBody = ResponseBuilder.buildFailed(error, null);
			response.getWriter().write(respBody);
		}
		catch (Throwable e)
		{
			LOG.error("Write forbidden response to client failed.", e);
		}
	}

}
