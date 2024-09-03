package com.mizhousoft.security.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mizhousoft.commons.web.ActionRespBuilder;
import com.mizhousoft.commons.web.ActionResponse;

/**
 * BigController
 *
 * @version
 */
@RestController
public class BigController
{
	@RequestMapping(value = "/big.action", method = RequestMethod.GET)
	public ActionResponse big()
	{
		ActionResponse response = ActionRespBuilder.buildSucceedResp();

		return response;
	}
}
