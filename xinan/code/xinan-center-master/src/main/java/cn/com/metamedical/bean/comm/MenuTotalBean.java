package cn.com.metamedical.bean.comm;

import java.util.ArrayList;
import java.util.List;

public class MenuTotalBean {
	private MenuHomeInfo homeInfo = new MenuHomeInfo();
	private MenuLogoInfo logoInfo = new MenuLogoInfo();
	private List<MenuInfo> menuInfo = new ArrayList<>();

	public MenuHomeInfo getHomeInfo() {
		return homeInfo;
	}

	public void setHomeInfo(MenuHomeInfo homeInfo) {
		this.homeInfo = homeInfo;
	}

	public MenuLogoInfo getLogoInfo() {
		return logoInfo;
	}

	public void setLogoInfo(MenuLogoInfo logoInfo) {
		this.logoInfo = logoInfo;
	}

	public List<MenuInfo> getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(List<MenuInfo> menuInfo) {
		this.menuInfo = menuInfo;
	}

}
