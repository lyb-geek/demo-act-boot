
package com.demo.data.init;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

public class DataInit {
	
	public static void main(String[] args) {
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		IdentityService is = engine.getIdentityService();
		// 添加用户组
		Group empGroup = saveGroup(is, "empGroup", "员工组");
		Group manageGroup = saveGroup(is, "manageGroup", "经理组");
		Group dirGroup = saveGroup(is, "dirGroup", "总监组");
		// 添加用户
		User empA = saveUser(is, "empa"); // 员工a
		User empB = saveUser(is, "empb"); // 员工b
		User managea = saveUser(is, "managea"); // 经理a
		User manageb = saveUser(is, "manageb"); // 经理b
		User dira = saveUser(is, "dira"); // 总监a
		// 绑定关系
		saveRel(is, empA, empGroup);
		saveRel(is, empB, empGroup);
		saveRel(is, managea, manageGroup);
		saveRel(is, manageb, manageGroup);
		saveRel(is, dira, dirGroup);
		
		engine.close();
	}
	
	static User saveUser(IdentityService is, String id) {
		User u = is.newUser(id);
		u.setPassword("123456");
		is.saveUser(u);
		return u;
	}
	
	static Group saveGroup(IdentityService is, String id, String name) {
		Group g = is.newGroup(id);
		g.setName(name);
		is.saveGroup(g);
		return g;
	}
	
	static void saveRel(IdentityService is, User u, Group g) {
		is.createMembership(u.getId(), g.getId());
	}
	
}
