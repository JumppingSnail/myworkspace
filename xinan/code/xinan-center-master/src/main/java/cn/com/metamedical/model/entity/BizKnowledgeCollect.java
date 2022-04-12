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
@TableName("biz_knowledge_collect")
public class BizKnowledgeCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String reposId;

    private String collectUserId;

    private String collectUserName;

    private LocalDateTime collectTime;

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

    public String getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(String collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getCollectUserName() {
        return collectUserName;
    }

    public void setCollectUserName(String collectUserName) {
        this.collectUserName = collectUserName;
    }

    public LocalDateTime getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(LocalDateTime collectTime) {
        this.collectTime = collectTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String REPOS_ID = "repos_id";

    public static final String COLLECT_USER_ID = "collect_user_id";

    public static final String COLLECT_USER_NAME = "collect_user_name";

    public static final String COLLECT_TIME = "collect_time";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizKnowledgeCollect{" +
        "id=" + id +
        ", reposId=" + reposId +
        ", collectUserId=" + collectUserId +
        ", collectUserName=" + collectUserName +
        ", collectTime=" + collectTime +
        ", remark=" + remark +
        "}";
    }
}
