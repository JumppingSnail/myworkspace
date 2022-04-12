package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-10-15
 */
@TableName("base_knowledge_catagory")
public class BaseKnowledgeCatagory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String catName;

    private Integer catSort;

    private String catIconUrl;

    private String catDescription;

    private String catState;

    private LocalDate createDate;

    private String remark;

    /**
     * 0-特殊不可删除；1-普通
     */
    private String catType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getCatSort() {
        return catSort;
    }

    public void setCatSort(Integer catSort) {
        this.catSort = catSort;
    }

    public String getCatIconUrl() {
        return catIconUrl;
    }

    public void setCatIconUrl(String catIconUrl) {
        this.catIconUrl = catIconUrl;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatState() {
        return catState;
    }

    public void setCatState(String catState) {
        this.catState = catState;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }

    public static final String ID = "id";

    public static final String CAT_NAME = "cat_name";

    public static final String CAT_SORT = "cat_sort";

    public static final String CAT_ICON_URL = "cat_icon_url";

    public static final String CAT_DESCRIPTION = "cat_description";

    public static final String CAT_STATE = "cat_state";

    public static final String CREATE_DATE = "create_date";

    public static final String REMARK = "remark";

    public static final String CAT_TYPE = "cat_type";

    @Override
    public String toString() {
        return "BaseKnowledgeCatagory{" +
        "id=" + id +
        ", catName=" + catName +
        ", catSort=" + catSort +
        ", catIconUrl=" + catIconUrl +
        ", catDescription=" + catDescription +
        ", catState=" + catState +
        ", createDate=" + createDate +
        ", remark=" + remark +
        ", catType=" + catType +
        "}";
    }
}
