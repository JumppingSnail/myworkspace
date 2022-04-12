package cn.com.metamedical.bean.repos;

import java.util.ArrayList;
import java.util.List;

public class SectionInfoVO {
	private String sectionId;
	private String sectionName;
	private List<SectionInfoVO> childSectionList = new ArrayList<>();

	public List<SectionInfoVO> getChildSectionList() {
		return childSectionList;
	}

	public void setChildSectionList(List<SectionInfoVO> childSectionList) {
		this.childSectionList = childSectionList;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

}
