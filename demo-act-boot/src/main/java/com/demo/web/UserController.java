
package com.demo.web;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "/login")
	public String login(String name, String passwd, HttpSession session, Model model, String flag) {
		boolean success = identityService.checkPassword(name, passwd);
		if (success) {
			session.setAttribute("user", name);
			return "main";
		} else {
			if (StringUtils.isNotBlank(flag)) {
				model.addAttribute("loginMsg", "用户名或者密码错误");
			}
		}

		return "login";

	}

}
