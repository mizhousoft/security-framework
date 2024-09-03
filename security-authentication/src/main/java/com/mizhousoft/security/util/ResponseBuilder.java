package com.mizhousoft.security.util;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.json.JSONException;
import com.mizhousoft.commons.json.JSONUtils;
import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;
import com.mizhousoft.commons.web.i18n.util.I18nUtils;
import com.mizhousoft.security.AccountDetails;

/**
 * 响应构建器
 *
 * @version
 */
public abstract class ResponseBuilder
{
	/**
	 * 构建成功响应
	 * 
	 * @param accountDetails
	 * @return
	 * @throws JSONException
	 */
	public static String buildSucceed(AccountDetails accountDetails) throws JSONException
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();
		String result = JSONUtils.toJSONString(response);

		return result;
	}

	/**
	 * 构建成功响应
	 * 
	 * @return
	 * @throws JSONException
	 */
	public static String buildSucceed() throws JSONException
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();
		String result = JSONUtils.toJSONString(response);

		return result;
	}

	public static String buildSucceed(String location) throws JSONException
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();
		response.setLocation(location);
		String result = JSONUtils.toJSONString(response);

		return result;
	}

	/**
	 * 构建认证失败
	 * 
	 * @param error
	 * @param errorCode
	 * @return
	 * @throws JSONException
	 */
	public static String buildAuthcFailed(String error, String errorCode) throws JSONException
	{
		if (StringUtils.isBlank(error))
		{
			error = I18nUtils.getMessage("security.authentication.login.failed");
		}

		ActionResponse response = ActionRespBuilder.buildFailedResp(error, errorCode);
		String result = JSONUtils.toJSONString(response);

		return result;
	}

	public static String buildForbidden() throws JSONException
	{
		String error = I18nUtils.getMessage("security.authentication.access.deny");

		ActionResponse response = ActionRespBuilder.buildFailedResp(error);
		String result = JSONUtils.toJSONString(response);

		return result;
	}

	public static String buildUnauthorized() throws JSONException
	{
		return buildUnauthorized(null);
	}

	public static String buildUnauthorized(String location) throws JSONException
	{
		String error = I18nUtils.getMessage("security.authentication.login.first");

		return buildFailed(error, location);
	}

	public static String buildFailed(String error, String location) throws JSONException
	{
		ActionResponse response = ActionRespBuilder.buildFailedResp(error);
		response.setLocation(location);
		String result = JSONUtils.toJSONString(response);

		return result;
	}
}
