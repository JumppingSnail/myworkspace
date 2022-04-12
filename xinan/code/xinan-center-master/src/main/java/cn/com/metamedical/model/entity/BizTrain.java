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
@TableName("biz_train")
public class BizTrain implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String trainSubject;

    private String trainPlace;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 1-草稿、2-进行中、3-已结束、4-已取消
     */
    private String trainState;

    /**
     * 1-全公开，2-指定医院，3-指定医生
     */
    private String objectType;

    private String trainContent;

    private LocalDateTime updateTime;

    private LocalDate createDate;

    private String createUserName;

    private String remark;

    private String createUserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainSubject() {
        return trainSubject;
    }

    public void setTrainSubject(String trainSubject) {
        this.trainSubject = trainSubject;
    }

    public String getTrainPlace() {
        return trainPlace;
    }

    public void setTrainPlace(String trainPlace) {
        this.trainPlace = trainPlace;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTrainState() {
        return trainState;
    }

    public void setTrainState(String trainState) {
        this.trainState = trainState;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public static final String ID = "id";

    public static final String TRAIN_SUBJECT = "train_subject";

    public static final String TRAIN_PLACE = "train_place";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String TRAIN_STATE = "train_state";

    public static final String OBJECT_TYPE = "object_type";

    public static final String TRAIN_CONTENT = "train_content";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_DATE = "create_date";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String REMARK = "remark";

    public static final String CREATE_USER_ID = "create_user_id";

    @Override
    public String toString() {
        return "BizTrain{" +
        "id=" + id +
        ", trainSubject=" + trainSubject +
        ", trainPlace=" + trainPlace +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", trainState=" + trainState +
        ", objectType=" + objectType +
        ", trainContent=" + trainContent +
        ", updateTime=" + updateTime +
        ", createDate=" + createDate +
        ", createUserName=" + createUserName +
        ", remark=" + remark +
        ", createUserId=" + createUserId +
        "}";
    }
}
