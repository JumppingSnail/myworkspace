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
@TableName("biz_knowledge_content")
public class BizKnowledgeContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String reposId;

    private String sectionName;

    private Integer sectionLevel;

    private String pSectionId;

    private Integer sectionSort;

    private String sectionContent;

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

    public String getReposId() {
        return reposId;
    }

    public void setReposId(String reposId) {
        this.reposId = reposId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getSectionLevel() {
        return sectionLevel;
    }

    public void setSectionLevel(Integer sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public String getpSectionId() {
        return pSectionId;
    }

    public void setpSectionId(String pSectionId) {
        this.pSectionId = pSectionId;
    }

    public Integer getSectionSort() {
        return sectionSort;
    }

    public void setSectionSort(Integer sectionSort) {
        this.sectionSort = sectionSort;
    }

    public String getSectionContent() {
        return sectionContent;
    }

    public void setSectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
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

    public static final String REPOS_ID = "repos_id";

    public static final String SECTION_NAME = "section_name";

    public static final String SECTION_LEVEL = "section_level";

    public static final String P_SECTION_ID = "p_section_id";

    public static final String SECTION_SORT = "section_sort";

    public static final String SECTION_CONTENT = "section_content";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizKnowledgeContent{" +
        "id=" + id +
        ", reposId=" + reposId +
        ", sectionName=" + sectionName +
        ", sectionLevel=" + sectionLevel +
        ", pSectionId=" + pSectionId +
        ", sectionSort=" + sectionSort +
        ", sectionContent=" + sectionContent +
        ", createTime=" + createTime +
        ", createUserName=" + createUserName +
        ", createUserId=" + createUserId +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
