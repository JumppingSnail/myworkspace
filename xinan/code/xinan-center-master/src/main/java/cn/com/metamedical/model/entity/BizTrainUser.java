package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 记录该培训所涉及的医生
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
@TableName("biz_train_user")
public class BizTrainUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String trainId;

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String TRAIN_ID = "train_id";

    public static final String USER_ID = "user_id";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizTrainUser{" +
        "id=" + id +
        ", trainId=" + trainId +
        ", userId=" + userId +
        ", remark=" + remark +
        "}";
    }
}
