package com.mizhousoft.security.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;

/**
 * LoginController
 *
 * @version
 */
@RestController
public class LoginController
{
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ActionResponse login()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}

	@RequestMapping(value = "/accountLogin.action", method = RequestMethod.POST)
	public ActionResponse accountLogin()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}
}
