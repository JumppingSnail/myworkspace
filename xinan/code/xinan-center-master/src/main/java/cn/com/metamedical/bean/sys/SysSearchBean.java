package cn.com.metamedical.bean.sys;

import org.apache.commons.lang3.StringUtils;

import cn.com.metamedical.bean.comm.BaseBean;

public class SysSearchBean extends BaseBean {
	private String userCodeSearch;
	private String userNameSearch;
	private String userStateSearch;
	private String orgIdSearch;
	private String userTypeSearch;

	public String getUserCode4Like() {
		if (StringUtils.isNotBlank(userCodeSearch)) {
			return "%" + userCodeSearch + "%";
		}
		return "";
	}

	public String getUserName4Like() {
		if (StringUtils.isNotBlank(userNameSearch)) {
			return "%" + userNameSearch + "%";
		}
		return "";
	}
	
	public String getUserCodeSearch() {
		return userCodeSearch;
	}

	public void setUserCodeSearch(String userCodeSearch) {
		this.userCodeSearch = userCodeSearch;
	}

	public String getUserNameSearch() {
		return userNameSearch;
	}

	public void setUserNameSearch(String userNameSearch) {
		this.userNameSearch = userNameSearch;
	}

	public String getUserStateSearch() {
		return userStateSearch;
	}

	public void setUserStateSearch(String userStateSearch) {
		this.userStateSearch = userStateSearch;
	}

	public String getOrgIdSearch() {
		return orgIdSearch;
	}

	public void setOrgIdSearch(String orgIdSearch) {
		this.orgIdSearch = orgIdSearch;
	}

	public String getUserTypeSearch() {
		return userTypeSearch;
	}

	public void setUserTypeSearch(String userTypeSearch) {
		this.userTypeSearch = userTypeSearch;
	}
}
