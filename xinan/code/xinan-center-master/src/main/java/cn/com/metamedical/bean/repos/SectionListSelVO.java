package cn.com.metamedical.bean.repos;

import cn.com.metamedical.model.entity.BizKnowledgeContent;

import java.util.ArrayList;
import java.util.List;

public class SectionListSelVO {
	private String id;
	private String title;
	private boolean spread = true;
	private boolean checked = false;
	private Integer level;
	private List<SectionListSelVO> children = new ArrayList<>();

	public SectionListSelVO() {

	}

	public SectionListSelVO(BizKnowledgeContent entity) {
		this.setId(entity.getId());
		this.setTitle(entity.getSectionName());
		this.setLevel(entity.getSectionLevel());
		this.setChildren(new ArrayList<>());
	}

	public List<SectionListSelVO> getChildren() {
		return children;
	}

	public void setChildren(List<SectionListSelVO> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
