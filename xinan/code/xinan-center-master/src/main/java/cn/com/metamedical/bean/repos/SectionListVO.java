package cn.com.metamedical.bean.repos;

import java.util.ArrayList;
import java.util.List;

import cn.com.metamedical.model.entity.BizKnowledgeContent;

public class SectionListVO extends BizKnowledgeContent {
	private List<SectionListVO> children = new ArrayList<>();
	
	public SectionListVO() {
		
	}
	
	public SectionListVO(BizKnowledgeContent entity) {
		this.setId(entity.getId());
		this.setpSectionId(entity.getpSectionId());
		this.setReposId(entity.getReposId());
		this.setSectionLevel(entity.getSectionLevel());
		this.setSectionName(entity.getSectionName());
		this.setSectionSort(entity.getSectionSort());
		this.setChildren(new ArrayList<>());
	}

	public List<SectionListVO> getChildren() {
		return children;
	}

	public void setChildren(List<SectionListVO> children) {
		this.children = children;
	}

}
