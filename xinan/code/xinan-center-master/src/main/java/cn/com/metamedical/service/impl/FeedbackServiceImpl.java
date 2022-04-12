package cn.com.metamedical.service.impl;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.other.FeedbackSearchBean;
import cn.com.metamedical.mapper.BizFeedbackMapper;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.service.IFeedbackService;
import cn.com.metamedical.util.tool.CommonTool;

@Service("feedbackService")
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	private CommonTool commonTool;

	@Autowired
	private BizFeedbackMapper bizFeedbackMapper;

	@Override
	public IPage<Map<String, Object>> feedbackList(FeedbackSearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());

		return bizFeedbackMapper.feedbackList(page, searchBean.getFeedbackContent4Like(),
				searchBean.getDealStateSearch());
	}

	@Override
	public String saveFeedbackInfo(UserBean currentUser, BizFeedback entity) {
		if (StringUtils.isBlank(entity.getDealState())) {
			entity.setDealState("2");
		}
		entity.setDealTime(LocalDateTime.now());
		entity.setDealUserId(currentUser.getUserId());
		entity.setDealUserName(currentUser.getRealName());
		bizFeedbackMapper.updateById(entity);

		return "ok";
	}

}
