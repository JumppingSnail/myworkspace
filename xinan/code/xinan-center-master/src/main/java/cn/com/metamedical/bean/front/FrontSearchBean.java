package cn.com.metamedical.bean.front;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.metamedical.bean.comm.BaseBean;

public class FrontSearchBean extends BaseBean {
	private String fullContentSearch;
	private String catagoryIdSearch;
	private String reposId;
	private String sectionId;
	private String trainId;
	private String userCode;
	private String password;
	private String oldPassword;
	private String newPassword;
	private String subjectId;
	private List<String> keywordIdList = new ArrayList<String>();

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public List<String> getKeywordIdList() {
		return keywordIdList;
	}

	public void setKeywordIdList(List<String> keywordIdList) {
		this.keywordIdList = keywordIdList;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getReposId() {
		return reposId;
	}

	public void setReposId(String reposId) {
		this.reposId = reposId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getFullContentSearch() {
		return fullContentSearch;
	}

	public void setFullContentSearch(String fullContentSearch) {
		this.fullContentSearch = fullContentSearch;
	}

	public String getCatagoryIdSearch() {
		return catagoryIdSearch;
	}

	public void setCatagoryIdSearch(String catagoryIdSearch) {
		this.catagoryIdSearch = catagoryIdSearch;
	}

	public String getFullContent4Like() {
		if (StringUtils.isNotBlank(fullContentSearch)) {
			return "%" + fullContentSearch + "%";
		}
		return "";
	}
}
