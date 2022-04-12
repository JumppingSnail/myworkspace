package cn.com.metamedical.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.front.FrontSearchBean;
import cn.com.metamedical.bean.medical.MedicalCaseDetailsBean;
import cn.com.metamedical.bean.medical.MedicalSearchBean;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.model.entity.BizKnowledgeCollect;
import cn.com.metamedical.model.entity.BizKnowledgeMark;
import cn.com.metamedical.service.IAuthService;
import cn.com.metamedical.service.IBizMedicalCaseService;
import cn.com.metamedical.service.ICatagoryService;
import cn.com.metamedical.service.IFrontpageService;
import cn.com.metamedical.service.ISysManageService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;

@RestController
@RequestMapping("/front")
public class FrontpageController {
	@Autowired
	private ControllerTool controllerTool;

	@Autowired
	private IFrontpageService frontpageService;

	@Autowired
	private ICatagoryService catagoryService;
	
	@Autowired
	private IAuthService authService;
	
	@Autowired
	private ISysManageService sysManageService;

	@Resource
	private IBizMedicalCaseService medicalCaseService;
	
	/**
	 * 手机登录接口
	 * 参数：userCode（用户手机号）、password（密码）
	 * 返回：
	 * {
  			"result": "ok",
  			"data": "", // token
  			"message": ""
		}
	 */
	@PostMapping("/login.do")
	public Map<String, Object> login(@RequestBody FrontSearchBean bean) {
		Map<String, Object> resMap = authService.login(bean.getUserCode(), bean.getPassword());

		if (resMap.get("error") != null) {
			return controllerTool.error(resMap.get("error").toString(), "Error Happens!");
		}

		String token = resMap.get("token").toString();
		UserBean userBean = (UserBean) resMap.get("user");

		JvmCacheTool.putTokenInfo(token, userBean);

		return controllerTool.success(token);
	}
	
	
	/**
	 * 获取首页栏目名称列表
	 * 参数：
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"cat_name":"",
      			"cat_icon_url":"",
      			"cat_description":""
    		}],
  			"message": ""
		}
	 */
	@PostMapping("/allCatagorylist.do")
	public Map<String, Object> allCatagorylist() {
		List<Map<String, Object>> resList = catagoryService.allCatagorylist();
		
		Map<String, Object> moreMap = new HashMap<String, Object>();
		moreMap.put("cat_name", "更多");
		moreMap.put("cat_icon_url", "/upload_files/xinan/more.png");
		resList.add(moreMap);
		
		return controllerTool.success(resList);
	}
	
	/**
	 * 获取首页推荐文章列表
	 * 参数：catagoryIdSearch（指定的栏目ID，空代表全部）， page（默认1），limit
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"repos_title":"",
      			"publish_date":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"repos_drug":"",
      			"cat_name":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/commendKnowledgeList.do")
	public Map<String, Object> commendKnowledgeList(@RequestBody FrontSearchBean bean) {
		return controllerTool.success(frontpageService.commendKnowledgeList(bean));
	}

	/**
	 * 获取首页最新文章列表
	 * 参数：catagoryIdSearch（指定的栏目ID，空代表全部）， page（默认1），limit
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"repos_title":"",
      			"publish_date":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"repos_drug":"",
      			"cat_name":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/newKnowledgeList.do")
	public Map<String, Object> newKnowledgeList(@RequestBody FrontSearchBean bean) {
		return controllerTool.success(frontpageService.newKnowledgeList(bean));
	}

	/**
	 * 获取全文检索文章列表
	 * 参数：catagoryIdSearch（指定的栏目ID，空代表全部），fullContentSearch（待查询内容）， page（默认1），limit
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"repos_title":"",
      			"publish_date":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"create_time":"",
      			"cat_name":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/fullSearchList.do")
	public Map<String, Object> fullSearchList(@RequestBody FrontSearchBean bean) {
		IPage<Map<String, Object>> page = frontpageService.fullSearchList(bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("count", page.getTotal());
        resMap.put("list", page.getRecords());
        return controllerTool.success(resMap);
	}

	/**
	 * 获取我的——我的收藏文章列表
	 * 参数： page（默认1），limit
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"repos_id":"",
      			"repos_title":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"cat_name":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/myCollectList.do")
	public Map<String, Object> myCollectList(HttpServletRequest request, FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		return controllerTool.success(frontpageService.myCollectList(currentUser, bean));
	}

	/**
	 * 获取我的——我的反馈列表
	 * 参数： page（默认1），limit
	 * 返回：
	 * { 				
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"repos_id":"",
      			"section_id":"",
      			"cat_name":"",
      			"repos_title":"",
      			"section_name":"",
      			"select_content":"",
      			"feedback_content":"",
      			"feedback_time":"",
      			"deal_content":"",
      			"deal_time":"",
      			"deal_user_id":"",
      			"deal_user_name":"",
      			"deal_state":"",
      			
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/myFeedbackList.do")
	public Map<String, Object> myFeedbackList(HttpServletRequest request, @RequestBody FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		return controllerTool.success(frontpageService.myFeedbackList(currentUser, bean));
	}

	/**
	 * 获取培训通知列表
	 * 参数： page（默认1），limit
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"train_subject":"",
      			"train_place":"",
      			"start_time":"",
      			"train_state":"",
      			
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/myTrainList.do")
	public Map<String, Object> myTrainList(HttpServletRequest request, @RequestBody FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		return controllerTool.success(frontpageService.myTrainList(currentUser, bean));
	}

	/**
	 * 依据ID获取培训详细内容
	 * 参数： trainId（培训ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": {
      			"train_id":"",
      			"train_subject":"",
      			"train_place":"",
      			"start_time":"",
      			"end_time":"",
      			"train_state":"",
      			"train_content":"",
      			
    		},
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/myTrainDetail.do")
	public Map<String, Object> myTrainDetail(@RequestBody FrontSearchBean bean) {
		return controllerTool.success(frontpageService.myTrainDetail(bean.getTrainId()));
	}
	
	/**
	 * 在阅读中，设置反馈内容
	 * 参数： reposId（文章ID）、sectionId（章节ID）、feedbackContent（反馈内容）、selectContent（页面选中的内容）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": "ok",
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/saveMyFeedback.do")
	public Map<String, Object> saveMyFeedback(HttpServletRequest request, @RequestBody BizFeedback entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(frontpageService.saveMyFeedback(currentUser, entity));
	}
	
	/**
	 * 在阅读中，设置对该文章的收藏状态
	 * 参数： reposId（文章ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": "ok",
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/saveOrDeleteMyCollect.do")
	public Map<String, Object> saveOrDeleteMyCollect(HttpServletRequest request, @RequestBody BizKnowledgeCollect entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(frontpageService.saveOrDeleteMyCollect(currentUser, entity));
	}
	
	/**
	 * 在阅读中，设置对该文章的书签
	 * 参数： reposId（文章ID）、sectionId（章节ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": "ok",
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/saveMyMark.do")
	public Map<String, Object> saveMyMark(HttpServletRequest request, @RequestBody BizKnowledgeMark entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(frontpageService.saveMyMark(currentUser, entity));
	}
	
	/**
	 * 依据文章ID，获取章节列表
	 * 参数： reposId（文章ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": [{
      			"sectionId":"",
      			"sectionName":"",
      			"childSectionList":[{
      				"sectionId":"",
      				"sectionName":"",
      			},{...}],
    		}],
  			"message": ""
		}
	 */
	@PostMapping("/getSectionList.do")
	public Map<String, Object> getSectionList(@RequestBody FrontSearchBean bean) {
		return controllerTool.success(frontpageService.getSectionList(bean));
	}
	
	/**
	 * 依据文章ID，获取书签列表
	 * 参数： reposId（文章ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"create_time":"",
      			"mark_content":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/getMarkList.do")
	public Map<String, Object> getMarkList(HttpServletRequest request, @RequestBody FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(frontpageService.getMarkList(currentUser, bean));
	}
	
	/**
	 * 依据文章ID，获取文章首页信息
	 * 参数： reposId（文章ID）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": {
      			"repos_id":"",
      			"repos_title":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"repos_drug":"",
      			"collect_flag":"", // 收藏标记，1-收藏，0-未收藏
      			"cat_name":"", 
    		},
  			"message": ""
		}
	 */
	@PostMapping("/fetchKnowledgeMainInfo.do")
	public Map<String, Object> fetchKnowledgeMainInfo(HttpServletRequest request, @RequestBody FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = null;
		if(StringUtils.isNotBlank(token)) {
			currentUser = JvmCacheTool.getTokenInfo(token);
		}
		
		return controllerTool.success(frontpageService.fetchKnowledgeMainInfo(currentUser, bean));
	}
	
	/**
	 * 依据文章ID和章节ID，获取文章章节信息
	 * 参数： reposId（文章ID）、sectionId（章节ID），如果章节ID为空，代表不是书籍，返回全部章节内容；如果传入章节ID，则返回该章节内容
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": [{
      			"section_id":"",
      			"section_name":"",
      			"section_content":"",
    		}],
  			"message": ""
		}
	 */
	@PostMapping("/fetchKnowledgeContent.do")
	public Map<String, Object> fetchKnowledgeContent(@RequestBody FrontSearchBean bean) {
		return controllerTool.success(frontpageService.fetchKnowledgeContent(bean));
	}
	
	/**
	 * 获取当前用户信息
	 * 参数： 
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": {
      			"userId":"",
      			"userType":"", // 0-超级管理员, 1-管理员, 2-研究员, 3-医生, 4-其他
      			"userCode":"",
      			"realName":"",
      			"orgId":"", 
      			"orgName":"", 
      			"menuBean":{}, 
      			"roles":[{}], 
    		},
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/fetchCurrentUserInfo.do")
	public Map<String, Object> fetchCurrentUserInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		return controllerTool.responseToClient(currentUser);
	}
	
	/**
	 * 修改密码
	 * 参数： oldPassword（旧密码）、newPassword（新密码）
	 * 返回：
	 * { 	
  			"result": "ok",
  			"data": "", // "ok"-正确，"weak"-新密码强度不够，"314"-密码不正确
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/changePassword.do")
	public Map<String, Object> changePassword(HttpServletRequest request, @RequestBody FrontSearchBean bean) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		String result = sysManageService.changeCurrentUserPassword(currentUser, currentUser.getUserId(),
				bean.getOldPassword(), bean.getNewPassword());
		return controllerTool.responseToClient(result, result);
	}

	@UserLoginToken
	@PostMapping("/getMedicalCaseListByPhone.do")
	public Map<String, Object> getMedicalCaseListByPhone(@RequestBody MedicalSearchBean bean) {
		IPage<Map<String, Object>> page = medicalCaseService.selectMedicalCaseListByPhone(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("count", page.getTotal());
		resMap.put("list", page.getRecords());
		return controllerTool.success(resMap);
	}

	//根据case获取详情信息
	@UserLoginToken
	@PostMapping("/getMedicalCaseInfo.do")
	public Map<String, Object> getMedicalCaseInfo(@RequestBody MedicalCaseDetailsBean bean) {
		return controllerTool.responseToClient(medicalCaseService.selectMedicalCaseDetailsByPhone(bean));
	}

	
	
	/**
	 * 获取关键字列表
	 * 参数：subjectId（指定的专题ID）
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"keyword_name":"",
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/allKeywordList.do")
	public Map<String, Object> allKeywordList(@RequestBody FrontSearchBean bean) {
		List<Map<String, Object>> resList = frontpageService.allKeywordList(bean);
		
		return controllerTool.success(resList);
	}
	
	/**
	 * 获取消渴肾病类型
	 * 参数：
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"subject_name":"",
      			"image_url":"",
      			"subject_description":""
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/allSubjectList.do")
	public Map<String, Object> allSubjectList() {
		List<Map<String, Object>> resList = frontpageService.allSubjectList();
		
		return controllerTool.success(resList);
	}
	
	/**
	 * 获取消渴肾病相关文章
	 * 参数：subjectId（指定的专题ID）, catagoryIdSearch（指定的栏目ID，空代表全部），keywordIdList（关键字列表，为空代表全部关键字）， page（默认1），limit
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{ 
      			"id":"",
      			"cat_name":"",
      			"repos_title":"",
      			"repos_author":"",
      			"repos_digest":"",
      			"create_time":"",
      			"keyword_name": "关键字1；关键字2"
    		}],
  			"message": ""
		}
	 */
	@UserLoginToken
	@PostMapping("/subjectDetailList.do")
	public Map<String, Object> subjectDetailList(@RequestBody FrontSearchBean bean) {
		IPage<Map<String, Object>> page = frontpageService.subjectDetailList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("count", page.getTotal());
		resMap.put("list", page.getRecords());
		return controllerTool.success(resMap);
	}
	
	/**
	 * 获取首页栏目名称列表
	 * 参数：
	 * 返回：
	 * {
  			"result": "ok",
  			"data": [{
      			"id":"",
      			"cat_name":"",
      			"cat_icon_url":"",
      			"cat_description":""
    		}],
  			"message": ""
		}
	 */
	@PostMapping("/allCatagorylistForTop.do")
	public Map<String, Object> allCatagorylistForTop() {
		List<Map<String, Object>> resList = catagoryService.allCatagorylist();
		resList.forEach(row->{
			if(row.get("cat_name").equals("医案")) {
				row.put("id", "1");
			}
		});
		
		return controllerTool.success(resList);
	}
}
