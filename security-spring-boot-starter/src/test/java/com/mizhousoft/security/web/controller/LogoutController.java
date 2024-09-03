package com.mizhousoft.security.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;

/**
 * LogoutController
 *
 * @version
 */
@RestController
public class LogoutController
{
	@RequestMapping(value = "/logout.action", method = RequestMethod.POST)
	public ActionResponse logout()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}

}
