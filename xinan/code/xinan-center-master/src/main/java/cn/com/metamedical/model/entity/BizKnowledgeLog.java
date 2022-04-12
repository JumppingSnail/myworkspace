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
@TableName("biz_knowledge_log")
public class BizKnowledgeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String reposId;

    private String changeContent;

    private LocalDateTime createTime;

    private String createUserName;

    private String createUserId;

    private String remark;


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

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
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

    public static final String REPOS_ID = "repos_id";

    public static final String CHANGE_CONTENT = "change_content";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizKnowledgeLog{" +
        "id=" + id +
        ", reposId=" + reposId +
        ", changeContent=" + changeContent +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", createUserId=" + createUserId +
        ", remark=" + remark +
        "}";
    }
}
