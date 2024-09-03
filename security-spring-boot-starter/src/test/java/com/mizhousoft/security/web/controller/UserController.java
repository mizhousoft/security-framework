package com.mizhousoft.security.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;

/**
 * TestController
 * 
 * @version
 */
@RestController
public class UserController
{
	@RequestMapping(value = "/user.action", method = RequestMethod.GET)
	public ActionResponse test()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}
}
