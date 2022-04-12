package cn.com.metamedical.bean.comm;

import java.util.ArrayList;
import java.util.List;

public class MenuInfo {
	private String id = "";
	private String pid = "";
	private String title = "";
	private String icon = "";
	private String href = "";
	private String target = "_self";
	private List<MenuInfo> child = new ArrayList<>();

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<MenuInfo> getChild() {
		return child;
	}

	public void setChild(List<MenuInfo> child) {
		this.child = child;
	}

}
