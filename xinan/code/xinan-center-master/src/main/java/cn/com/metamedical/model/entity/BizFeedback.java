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
 * @since 2021-10-15
 */
@TableName("biz_feedback")
public class BizFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String reposId;

    private String sectionId;

    private String feedbackContent;

    /**
     * 1-待处理；2-跟进中；3-已处理
     */
    private String dealState;

    private String dealContent;

    private String feedbackUserId;

    private String feedbackUserName;

    private LocalDateTime feedbackTime;

    private String dealUserId;

    private String dealUserName;

    private LocalDateTime dealTime;

    private String remark;

    private String selectContent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getFeedbackUserId() {
        return feedbackUserId;
    }

    public void setFeedbackUserId(String feedbackUserId) {
        this.feedbackUserId = feedbackUserId;
    }

    public String getFeedbackUserName() {
        return feedbackUserName;
    }

    public void setFeedbackUserName(String feedbackUserName) {
        this.feedbackUserName = feedbackUserName;
    }

    public LocalDateTime getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(LocalDateTime feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    public String getDealUserName() {
        return dealUserName;
    }

    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    public LocalDateTime getDealTime() {
        return dealTime;
    }

    public void setDealTime(LocalDateTime dealTime) {
        this.dealTime = dealTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSelectContent() {
        return selectContent;
    }

    public void setSelectContent(String selectContent) {
        this.selectContent = selectContent;
    }

    public static final String ID = "id";

    public static final String REPOS_ID = "repos_id";

    public static final String SECTION_ID = "section_id";

    public static final String FEEDBACK_CONTENT = "feedback_content";

    public static final String DEAL_STATE = "deal_state";

    public static final String DEAL_CONTENT = "deal_content";

    public static final String FEEDBACK_USER_ID = "feedback_user_id";

    public static final String FEEDBACK_USER_NAME = "feedback_user_name";

    public static final String FEEDBACK_TIME = "feedback_time";

    public static final String DEAL_USER_ID = "deal_user_id";

    public static final String DEAL_USER_NAME = "deal_user_name";

    public static final String DEAL_TIME = "deal_time";

    public static final String REMARK = "remark";

    public static final String SELECT_CONTENT = "select_content";

    @Override
    public String toString() {
        return "BizFeedback{" +
        "id=" + id +
        ", reposId=" + reposId +
        ", sectionId=" + sectionId +
        ", feedbackContent=" + feedbackContent +
        ", dealState=" + dealState +
        ", dealContent=" + dealContent +
        ", feedbackUserId=" + feedbackUserId +
        ", feedbackUserName=" + feedbackUserName +
        ", feedbackTime=" + feedbackTime +
        ", dealUserId=" + dealUserId +
        ", dealUserName=" + dealUserName +
        ", dealTime=" + dealTime +
        ", remark=" + remark +
        ", selectContent=" + selectContent +
        "}";
    }
}
