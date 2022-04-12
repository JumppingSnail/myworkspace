package cn.com.metamedical.bean.train;

import cn.com.metamedical.bean.comm.BaseBean;
import org.apache.commons.lang3.StringUtils;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class TrainSearchBean extends BaseBean {

    private String trainSubject;

    private String trainState;

    public String getTrainSubjectSearchLike(){
        if (StringUtils.isNotBlank(trainSubject))
            return "%" + trainSubject + "%";
        return "";
    }

    public String getTrainSubject() {
        return trainSubject;
    }

    public void setTrainSubject(String trainSubject) {
        this.trainSubject = trainSubject;
    }

    public String getTrainState() {
        return trainState;
    }

    public void setTrainState(String trainState) {
        this.trainState = trainState;
    }
}
