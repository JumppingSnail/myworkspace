package cn.com.metamedical.bean.sys;

import java.util.ArrayList;
import java.util.List;

public class RoleVoBean {
	private String roleId;
	private List<TreeBean> menuList = new ArrayList<>();

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<TreeBean> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<TreeBean> menuList) {
		this.menuList = menuList;
	}

}
