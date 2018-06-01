
package com.demo.data.init;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public class VacationDeploy {
	
	public static void main(String[] args) {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 存储服务
		RepositoryService rs = engine.getRepositoryService();
		Deployment dep = rs.createDeployment().addClasspathResource("processes/vacation.bpmn").deploy();
		ProcessDefinition pd = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
		rs.addCandidateStarterGroup(pd.getId(), "empGroup");
		
		engine.close();
	}
	
}
