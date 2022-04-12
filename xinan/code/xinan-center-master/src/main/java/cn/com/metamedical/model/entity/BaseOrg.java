package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * <p>
 * 存储医院信息
 * </p>
 *
 * @author Allen
 * @since 2021-10-12
 */
@TableName("base_org")
public class BaseOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String orgName;

    private String orgSimpleName;

    private String orgPhone;

    private String orgAddress;

    private LocalDate createDate;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgSimpleName() {
        return orgSimpleName;
    }

    public void setOrgSimpleName(String orgSimpleName) {
        this.orgSimpleName = orgSimpleName;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String ORG_NAME = "org_name";

    public static final String ORG_SIMPLE_NAME = "org_simple_name";

    public static final String ORG_PHONE = "org_phone";

    public static final String ORG_ADDRESS = "org_address";

    public static final String CREATE_DATE = "create_date";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BaseOrg{" +
        "id=" + id +
        ", orgName=" + orgName +
        ", orgSimpleName=" + orgSimpleName +
        ", orgPhone=" + orgPhone +
        ", orgAddress=" + orgAddress +
        ", createDate=" + createDate +
        ", remark=" + remark +
        "}";
    }
}
