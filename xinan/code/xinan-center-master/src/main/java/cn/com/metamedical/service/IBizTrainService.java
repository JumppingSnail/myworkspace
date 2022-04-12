package cn.com.metamedical.service;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.train.TrainDeleteBean;
import cn.com.metamedical.bean.train.TrainSaveBean;
import cn.com.metamedical.bean.train.TrainSearchBean;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public interface IBizTrainService {

    IPage<Map<String, Object>> getBizTrainList(TrainSearchBean bean);

    String saveTrain(UserBean currentUser, TrainSaveBean item);

    Map<String, Object> queryTrainMap(UserBean currentUser, TrainDeleteBean bean);

    String deleteTrain(UserBean currentUser, TrainDeleteBean bean);

    String updateTrainState(UserBean currentUser, TrainDeleteBean bean);

    String updateEndTrainTask();


}
