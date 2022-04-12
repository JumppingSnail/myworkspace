package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-12-17
 */
@TableName("biz_case_doseage")
public class BizCaseDoseage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String caseId;

    private String doseageId;

    private String drugId;

    private String drugUsage;

    private Integer drugSort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDoseageId() {
        return doseageId;
    }

    public void setDoseageId(String doseageId) {
        this.doseageId = doseageId;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugUsage() {
        return drugUsage;
    }

    public void setDrugUsage(String drugUsage) {
        this.drugUsage = drugUsage;
    }

    public Integer getDrugSort() {
        return drugSort;
    }

    public void setDrugSort(Integer drugSort) {
        this.drugSort = drugSort;
    }

    public static final String ID = "id";

    public static final String CASE_ID = "case_id";

    public static final String DOSEAGE_ID = "doseage_id";

    public static final String DRUG_ID = "drug_id";

    public static final String DRUG_USAGE = "drug_usage";

    public static final String DRUG_SORT = "drug_sort";

    @Override
    public String toString() {
        return "BizCaseDoseage{" +
        "id=" + id +
        ", caseId=" + caseId +
        ", doseageId=" + doseageId +
        ", drugId=" + drugId +
        ", drugUsage=" + drugUsage +
        ", drugSort=" + drugSort +
        "}";
    }
}
