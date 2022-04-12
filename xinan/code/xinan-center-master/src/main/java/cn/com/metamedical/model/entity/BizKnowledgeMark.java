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
 * @since 2021-10-18
 */
@TableName("biz_knowledge_mark")
public class BizKnowledgeMark implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String sectionId;

    private String createUserId;

    private String createUserName;

    private LocalDateTime createTime;

    private String markContent;

    private String markSite;

    private String remark;

    private String reposId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getMarkContent() {
        return markContent;
    }

    public void setMarkContent(String markContent) {
        this.markContent = markContent;
    }

    public String getMarkSite() {
        return markSite;
    }

    public void setMarkSite(String markSite) {
        this.markSite = markSite;
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

    public static final String ID = "id";

    public static final String SECTION_ID = "section_id";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_TIME = "create_time";

    public static final String MARK_CONTENT = "mark_content";

    public static final String MARK_SITE = "mark_site";

    public static final String REMARK = "remark";

    public static final String REPOS_ID = "repos_id";

    @Override
    public String toString() {
        return "BizKnowledgeMark{" +
        "id=" + id +
        ", sectionId=" + sectionId +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", markContent=" + markContent +
        ", markSite=" + markSite +
        ", remark=" + remark +
        ", reposId=" + reposId +
        "}";
    }
}
