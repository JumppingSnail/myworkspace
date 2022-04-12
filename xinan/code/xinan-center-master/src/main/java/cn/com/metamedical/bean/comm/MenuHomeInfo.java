package cn.com.metamedical.bean.comm;

public class MenuHomeInfo {
	private String title = "首页";
	private String href = "main/main-page.html";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href + "?t=" + System.currentTimeMillis();
	}

}
