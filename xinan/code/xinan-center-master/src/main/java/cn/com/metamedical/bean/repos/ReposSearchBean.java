package cn.com.metamedical.bean.repos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.metamedical.bean.comm.BaseBean;

public class ReposSearchBean extends BaseBean {
	private String catNameSearch;
	private String reposTitleSearch;
	private String reposStateSearch;
	private String catId;
	private String reposId;
	private String createUserId;
	private String onlyUserFlag; // 1-表示仅显示我创建的知识
	private List<SectionListVO> sectionList = new ArrayList<>();

	public List<SectionListVO> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<SectionListVO> sectionList) {
		this.sectionList = sectionList;
	}

	public String getOnlyUserFlag() {
		return onlyUserFlag;
	}

	public void setOnlyUserFlag(String onlyUserFlag) {
		this.onlyUserFlag = onlyUserFlag;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getReposId() {
		return reposId;
	}

	public void setReposId(String reposId) {
		this.reposId = reposId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getReposTitleSearch() {
		return reposTitleSearch;
	}

	public void setReposTitleSearch(String reposTitleSearch) {
		this.reposTitleSearch = reposTitleSearch;
	}

	public String getReposStateSearch() {
		return reposStateSearch;
	}

	public void setReposStateSearch(String reposStateSearch) {
		this.reposStateSearch = reposStateSearch;
	}

	public String getCatNameSearch() {
		return catNameSearch;
	}

	public void setCatNameSearch(String catNameSearch) {
		this.catNameSearch = catNameSearch;
	}

	public String getCatName4Like() {
		if (StringUtils.isNotBlank(catNameSearch)) {
			return "%" + catNameSearch + "%";
		}
		return "";
	}

	public String getReposTitle4Like() {
		if (StringUtils.isNotBlank(reposTitleSearch)) {
			return "%" + reposTitleSearch + "%";
		}
		return "";
	}
}
