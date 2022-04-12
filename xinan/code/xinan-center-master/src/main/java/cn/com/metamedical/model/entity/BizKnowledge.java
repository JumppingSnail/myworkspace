package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
@TableName("biz_knowledge")
public class BizKnowledge implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String reposTitle;

    private String catId;

    /**
     * 0-未上架；1-已上架
     */
    private String reposState;

    /**
     * 0-不推荐；1-推荐
     */
    private String commendFlag;

    private String reposUrl;

    private LocalDate publishDate;

    private String reposAuthor;

    private String reposDigest;

    private String reposDrug;

    private LocalDateTime createTime;

    private String createUserName;

    private String createUserId;

    private LocalDateTime updateTime;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReposTitle() {
        return reposTitle;
    }

    public void setReposTitle(String reposTitle) {
        this.reposTitle = reposTitle;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getReposState() {
        return reposState;
    }

    public void setReposState(String reposState) {
        this.reposState = reposState;
    }

    public String getCommendFlag() {
        return commendFlag;
    }

    public void setCommendFlag(String commendFlag) {
        this.commendFlag = commendFlag;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getReposAuthor() {
        return reposAuthor;
    }

    public void setReposAuthor(String reposAuthor) {
        this.reposAuthor = reposAuthor;
    }

    public String getReposDigest() {
        return reposDigest;
    }

    public void setReposDigest(String reposDigest) {
        this.reposDigest = reposDigest;
    }

    public String getReposDrug() {
        return reposDrug;
    }

    public void setReposDrug(String reposDrug) {
        this.reposDrug = reposDrug;
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

    public static final String ID = "id";

    public static final String REPOS_TITLE = "repos_title";

    public static final String CAT_ID = "cat_id";

    public static final String REPOS_STATE = "repos_state";

    public static final String COMMEND_FLAG = "commend_flag";

    public static final String REPOS_URL = "repos_url";

    public static final String PUBLISH_DATE = "publish_date";

    public static final String REPOS_AUTHOR = "repos_author";

    public static final String REPOS_DIGEST = "repos_digest";

    public static final String REPOS_DRUG = "repos_drug";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizKnowledge{" +
        "id=" + id +
        ", reposTitle=" + reposTitle +
        ", catId=" + catId +
        ", reposState=" + reposState +
        ", commendFlag=" + commendFlag +
        ", reposUrl=" + reposUrl +
        ", publishDate=" + publishDate +
        ", reposAuthor=" + reposAuthor +
        ", reposDigest=" + reposDigest +
        ", reposDrug=" + reposDrug +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", createUserId=" + createUserId +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
