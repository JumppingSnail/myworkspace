package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录该培训所涉及的医院
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
@TableName("biz_train_hospital")
public class BizTrainHospital implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String trainId;

    private String orgId;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String TRAIN_ID = "train_id";

    public static final String ORG_ID = "org_id";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizTrainHospital{" +
        "id=" + id +
        ", trainId=" + trainId +
        ", orgId=" + orgId +
        ", remark=" + remark +
        "}";
    }
}
