package cn.com.metamedical.bean.comm;

public class BaseBean {
	private int page = 1;
	private int limit = 20;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
