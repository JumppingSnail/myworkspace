package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2021-10-12
 */
@TableName("base_user_org_rel")
public class BaseUserOrgRel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String orgId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ORG_ID = "org_id";

    @Override
    public String toString() {
        return "BaseUserOrgRel{" +
        "id=" + id +
        ", userId=" + userId +
        ", orgId=" + orgId +
        "}";
    }
}
