package com.mizhousoft.security;

/**
 * 登录方式
 *
 * @version
 */
public enum LoginMethodEnum
{
    // 帐号密码
	ACCOUNT_LOGIN("account"),
    // 手机号
	PHONE_NUMBER_LOGIN("phoneNumber"),
    // 苹果ID
	APPLE_ID_LOGIN("appleid"),
    // 微信开放平台
	WEIXIN_OPEN_LOGIN("weixinOpen"),
    // 微信小程序
	WEIXIN_MINI_LOGIN("weixinMini"),
    // 微信公众号
	WEIXIN_MP_LOGIN("weixinMP"),
    // 字节跳动小程序
	BYTEDANCE_MINI_LOGIN("byteDanceMini");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private LoginMethodEnum(String val)
	{
		this.value = val;
	}

	/**
	 * 值
	 */
	private final String value;

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public String getValue()
	{
		return value;
	}

	public boolean isSelf(String val)
	{
		return (this.value.equals(val));
	}

	/**
	 * 获取状态
	 * 
	 * @param status
	 * @return
	 */
	public LoginMethodEnum get(String val)
	{
		LoginMethodEnum[] values = LoginMethodEnum.values();
		for (LoginMethodEnum value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
