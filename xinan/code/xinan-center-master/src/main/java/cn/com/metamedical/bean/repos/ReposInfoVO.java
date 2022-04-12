package cn.com.metamedical.bean.repos;

import java.util.ArrayList;
import java.util.List;

import cn.com.metamedical.model.entity.BizKnowledge;

public class ReposInfoVO {
	private BizKnowledge reposBean;
	private List<SectionInfoVO> sectionList = new ArrayList<>();

	public BizKnowledge getReposBean() {
		return reposBean;
	}

	public void setReposBean(BizKnowledge reposBean) {
		this.reposBean = reposBean;
	}

	public List<SectionInfoVO> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<SectionInfoVO> sectionList) {
		this.sectionList = sectionList;
	}

}
