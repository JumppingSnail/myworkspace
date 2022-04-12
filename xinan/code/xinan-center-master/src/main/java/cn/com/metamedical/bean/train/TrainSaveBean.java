package cn.com.metamedical.bean.train;

import cn.com.metamedical.model.entity.BizTrain;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class TrainSaveBean extends BizTrain {

    private String trainMapIds;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public String getTrainMapIds() {
        return trainMapIds;
    }

    public void setTrainMapIds(String trainMapIds) {
        this.trainMapIds = trainMapIds;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
