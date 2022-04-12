package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-10-12
 */
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String menuName;

    private String menuUrl;

    private String menuIcon;

    private Integer menuSort;

    private Integer menuLevel;

    private String pMenuId;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getpMenuId() {
        return pMenuId;
    }

    public void setpMenuId(String pMenuId) {
        this.pMenuId = pMenuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String MENU_NAME = "menu_name";

    public static final String MENU_URL = "menu_url";

    public static final String MENU_ICON = "menu_icon";

    public static final String MENU_SORT = "menu_sort";

    public static final String MENU_LEVEL = "menu_level";

    public static final String P_MENU_ID = "p_menu_id";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "SysMenu{" +
        "id=" + id +
        ", menuName=" + menuName +
        ", menuUrl=" + menuUrl +
        ", menuIcon=" + menuIcon +
        ", menuSort=" + menuSort +
        ", menuLevel=" + menuLevel +
        ", pMenuId=" + pMenuId +
        ", remark=" + remark +
        "}";
    }
}
