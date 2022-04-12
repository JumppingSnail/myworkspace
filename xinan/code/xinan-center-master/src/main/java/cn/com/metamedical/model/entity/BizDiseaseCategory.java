package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2022-02-26
 */
@TableName("biz_disease_category")
public class BizDiseaseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String subjectName;

    private String imageUrl;

    private Integer subjectSort;

    private String subjectDescription;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSubjectSort() {
        return subjectSort;
    }

    public void setSubjectSort(Integer subjectSort) {
        this.subjectSort = subjectSort;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String SUBJECT_NAME = "subject_name";

    public static final String IMAGE_URL = "image_url";

    public static final String SUBJECT_SORT = "subject_sort";

    public static final String SUBJECT_DESCRIPTION = "subject_description";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizDiseaseCategory{" +
        "id=" + id +
        ", subjectName=" + subjectName +
        ", imageUrl=" + imageUrl +
        ", subjectSort=" + subjectSort +
        ", subjectDescription=" + subjectDescription +
        ", remark=" + remark +
        "}";
    }
}
