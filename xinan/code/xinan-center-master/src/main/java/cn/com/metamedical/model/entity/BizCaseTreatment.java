package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-12-14
 */
@TableName("biz_case_treatment")
public class BizCaseTreatment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String caseId;

    private String changeContent;

    private String treatName;

    private String treatDetail;

    private String treatUsage;

    private String treatResult;

    private Integer treatSort;

    private LocalDateTime createTime;

    private String createUserName;

    private String createUserId;

    private String remark;

    private String caseDiseaseId;

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

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getTreatName() {
        return treatName;
    }

    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    public String getTreatDetail() {
        return treatDetail;
    }

    public void setTreatDetail(String treatDetail) {
        this.treatDetail = treatDetail;
    }

    public String getTreatUsage() {
        return treatUsage;
    }

    public void setTreatUsage(String treatUsage) {
        this.treatUsage = treatUsage;
    }

    public String getTreatResult() {
        return treatResult;
    }

    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    public Integer getTreatSort() {
        return treatSort;
    }

    public void setTreatSort(Integer treatSort) {
        this.treatSort = treatSort;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String CASE_ID = "case_id";

    public static final String CHANGE_CONTENT = "change_content";

    public static final String TREAT_NAME = "treat_name";

    public static final String TREAT_DETAIL = "treat_detail";

    public static final String TREAT_USAGE = "treat_usage";

    public static final String TREAT_RESULT = "treat_result";

    public static final String TREAT_SORT = "treat_sort";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String REMARK = "remark";

    public String getCaseDiseaseId() {
        return caseDiseaseId;
    }

    public void setCaseDiseaseId(String caseDiseaseId) {
        this.caseDiseaseId = caseDiseaseId;
    }

    @Override
    public String toString() {
        return "BizCaseTreatment{" +
                "id='" + id + '\'' +
                ", caseId='" + caseId + '\'' +
                ", changeContent='" + changeContent + '\'' +
                ", treatName='" + treatName + '\'' +
                ", treatDetail='" + treatDetail + '\'' +
                ", treatUsage='" + treatUsage + '\'' +
                ", treatResult='" + treatResult + '\'' +
                ", treatSort=" + treatSort +
                ", createTime=" + createTime +
                ", createUserName='" + createUserName + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", remark='" + remark + '\'' +
                ", caseDiseaseId='" + caseDiseaseId + '\'' +
                '}';
    }
}
