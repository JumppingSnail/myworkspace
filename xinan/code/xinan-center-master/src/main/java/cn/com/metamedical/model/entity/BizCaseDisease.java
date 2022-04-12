package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-12-17
 */
@TableName("biz_case_disease")
public class BizCaseDisease implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String caseId;

    private String reposId;


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

    public String getReposId() {
        return reposId;
    }

    public void setReposId(String reposId) {
        this.reposId = reposId;
    }

    public static final String ID = "id";

    public static final String CASE_ID = "case_id";

    public static final String REPOS_ID = "repos_id";

    @Override
    public String toString() {
        return "BizCaseDisease{" +
        "id=" + id +
        ", caseId=" + caseId +
        ", reposId=" + reposId +
        "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BizCaseDisease that = (BizCaseDisease) o;
        return Objects.equals(caseId, that.caseId) &&
                Objects.equals(reposId, that.reposId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, reposId);
    }
}
