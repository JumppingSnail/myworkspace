package cn.com.metamedical.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.other.FeedbackSearchBean;
import cn.com.metamedical.model.entity.BizFeedback;

public interface IFeedbackService {
	public IPage<Map<String, Object>> feedbackList(FeedbackSearchBean bean);
	
	public String saveFeedbackInfo(UserBean currentUser, BizFeedback entity);
}
