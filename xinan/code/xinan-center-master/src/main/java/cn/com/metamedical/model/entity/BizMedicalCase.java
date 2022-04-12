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
 * @since 2021-12-16
 */
@TableName("biz_medical_case")
public class BizMedicalCase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String patientName;

    private Integer patientAge;

    private String patientSex;

    private String caseDescription;

    private LocalDateTime createTime;

    private String createUserName;

    private String createUserId;

    private LocalDateTime updateTime;

    private String remark;

    /**
     * 出处知识库ID
     */
    private String reposId;

    /**
     * 关联章节ID
     */
    private String sectionId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReposId() {
        return reposId;
    }

    public void setReposId(String reposId) {
        this.reposId = reposId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public static final String ID = "id";

    public static final String PATIENT_NAME = "patient_name";

    public static final String PATIENT_AGE = "patient_age";

    public static final String PATIENT_SEX = "patient_sex";

    public static final String CASE_DESCRIPTION = "case_description";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String REMARK = "remark";

    public static final String REPOS_ID = "repos_id";

    public static final String SECTION_ID = "section_id";

    @Override
    public String toString() {
        return "BizMedicalCase{" +
        "id=" + id +
        ", patientName=" + patientName +
        ", patientAge=" + patientAge +
        ", patientSex=" + patientSex +
        ", caseDescription=" + caseDescription +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", createUserId=" + createUserId +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        ", reposId=" + reposId +
        ", sectionId=" + sectionId +
        "}";
    }
}
