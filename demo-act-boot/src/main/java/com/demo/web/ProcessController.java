
package com.demo.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.util.ActUtils;
import com.demo.vo.ProcessVO;

@Controller
public class ProcessController {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService identityService;

	@RequestMapping(value = "/vac/start")
	public String start(Integer days, String reason, HttpSession session) {
		String userId = (String) session.getAttribute("user");
		identityService.setAuthenticatedUserId(userId);

		// 启动流程实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("vacationProcess");
		// 完成第一个任务
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("days", days);
		vars.put("reason", reason);
		taskService.claim(task.getId(), userId);
		taskService.complete(task.getId(), vars);
		return "redirect:/vac/list";
	}

	@RequestMapping(value = "/vac/list")
	public String list(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("user");
		// 用户参与的流程
		// List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().involvedUser(userId).list();
		List<ProcessInstance> pis = runtimeService.createProcessInstanceQuery().startedBy(userId).list();

		List<ProcessVO> result = new ArrayList<ProcessVO>();
		for (ProcessInstance pi : pis) {
			Integer days = (Integer) runtimeService.getVariable(pi.getId(), "days");
			String reason = (String) runtimeService.getVariable(pi.getId(), "reason");
			System.out.println(reason + "---" + days);
			ProcessVO v = new ProcessVO();
			v.setDays(days);
			v.setReason(reason);
			v.setDate(formatDate(pi.getStartTime()));
			v.setProcessInstanceId(pi.getId());
			result.add(v);
		}
		model.addAttribute("pis", result);
		return "vac/list";
	}

	private static String formatDate(Date d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(d);
		} catch (Exception e) {
			return "";
		}
	}

	@RequestMapping(value = "/getFlowImg/{processInstanceId}")
	public void getFlowImgByInstantId(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response) {
		try {
			System.out.println("processInstanceId:" + processInstanceId);
			ActUtils.getFlowImgByInstanceId(processInstanceId, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
