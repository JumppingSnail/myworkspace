package cn.com.metamedical.controller.other;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.other.FeedbackSearchBean;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.service.IFeedbackService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	private ControllerTool controllerTool;
	
	@Autowired
	private IFeedbackService feedbackService;
	
	@UserLoginToken
	@GetMapping("/feedbackList.do")
	public Map<String, Object> feedbackList(FeedbackSearchBean bean) {
		IPage<Map<String, Object>> page = feedbackService.feedbackList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@PostMapping("/saveFeedbackInfo.do")
	public Map<String, Object> saveFeedbackInfo(HttpServletRequest request, @RequestBody BizFeedback entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		return controllerTool.success(feedbackService.saveFeedbackInfo(currentUser, entity));
	}
	
}
