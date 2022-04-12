package cn.com.metamedical.bean.comm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userType;
	private String userCode;
	private String realName;
	private List<RoleBean> roles = new ArrayList<>();
	private String orgId;
	private String orgName;
	private MenuTotalBean menuBean;
	private Map<String, List<String>> menuFuncMap = new HashMap<>();

	// 此处预留用于今后可能的功能进行扩展
	private Map<String, Object> additionInfoMap = new HashMap<>();

	public Map<String, Object> getAdditionInfoMap() {
		return additionInfoMap;
	}

	public void setAdditionInfoMap(Map<String, Object> additionInfoMap) {
		this.additionInfoMap = additionInfoMap;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public MenuTotalBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuTotalBean menuBean) {
		this.menuBean = menuBean;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<RoleBean> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Map<String, List<String>> getMenuFuncMap() {
		return menuFuncMap;
	}

	public void setMenuFuncMap(Map<String, List<String>> menuFuncMap) {
		this.menuFuncMap = menuFuncMap;
	}

}
