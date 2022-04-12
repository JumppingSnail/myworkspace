package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
@TableName("base_param_dict")
public class BaseParamDict implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String paramCategory;

    private String paramName;

    private String paramValue;

    private Integer paramSort;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamCategory() {
        return paramCategory;
    }

    public void setParamCategory(String paramCategory) {
        this.paramCategory = paramCategory;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getParamSort() {
        return paramSort;
    }

    public void setParamSort(Integer paramSort) {
        this.paramSort = paramSort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String PARAM_CATEGORY = "param_category";

    public static final String PARAM_NAME = "param_name";

    public static final String PARAM_VALUE = "param_value";

    public static final String PARAM_SORT = "param_sort";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BaseParamDict{" +
        "id=" + id +
        ", paramCategory=" + paramCategory +
        ", paramName=" + paramName +
        ", paramValue=" + paramValue +
        ", paramSort=" + paramSort +
        ", remark=" + remark +
        "}";
    }
}
