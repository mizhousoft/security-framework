package com.mizhousoft.security.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;

/**
 * UserListController
 *
 * @version
 */
@RestController
public class UserListController
{
	@RequestMapping(value = "/list.action", method = RequestMethod.GET)
	public ActionResponse index()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}

	@RequestMapping(value = "/disable.action", method = RequestMethod.GET)
	public ActionResponse disable()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}
}
