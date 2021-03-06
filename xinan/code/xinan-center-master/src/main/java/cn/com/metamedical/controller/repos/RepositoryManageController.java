package cn.com.metamedical.controller.repos;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.repos.ReposSearchBean;
import cn.com.metamedical.model.entity.BizKnowledge;
import cn.com.metamedical.model.entity.BizKnowledgeContent;
import cn.com.metamedical.service.IRepositoryManageService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;

@RestController
@RequestMapping("/repos")
public class RepositoryManageController {
	@Autowired
	private ControllerTool controllerTool;
	
	@Autowired
	private IRepositoryManageService repositoryService;
	
	@UserLoginToken
	@GetMapping("/reposList.do")
	public Map<String, Object> reposList(HttpServletRequest request, ReposSearchBean bean) {
		if("1".equals(bean.getOnlyUserFlag())) {
			String token = request.getHeader(CommonConstant.TOKEN_KEY);
			UserBean currentUser = JvmCacheTool.getTokenInfo(token);
			bean.setCreateUserId(currentUser.getUserId());
		}
		
		IPage<Map<String, Object>> page = repositoryService.reposList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@GetMapping("/reposLog.do")
	public Map<String, Object> reposLog(ReposSearchBean bean) {
		IPage<Map<String, Object>> page = repositoryService.reposLog(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	/**
	 * ????????????????????????
	 * ?????????id  // ??????id
	 * ?????????
	 * {
	 *			"reposBean": {
	 *				"id": 
	 *				"reposTitle": //????????????
	 *				"catId": //??????ID
	 *				"publishDate": //????????????
	 *				"reposAuthor": //??????
	 *				"reposDigest": //????????????
	 *				"reposDrug": //??????
	 *			}
	 * 			"sectionList": [
	 * 			{
	 * 				"sectionId":
	 * 				"sectionName":
	 * 				"childSectionList": [...]
	 * 			},...
	 * 			]
	 * 			"sectionName": // ????????????
	 * 			"sectionContent": // ??????
	 * }
	 * 
	 * */
	@UserLoginToken
	@GetMapping("/fetchRepositoryInfo.do")
	public Map<String, Object> fetchRepositoryInfo(String id) {
		return controllerTool.success(repositoryService.fetchRepositoryInfo(id));
	}
	
	/**
	 * ????????????????????????
	 * ?????????sectionId  ??????id
	 * {
	 *			"id": // ??????id
	 * 			"reposId": // ?????????id
	 * 			"sectionName": // ????????????
	 * 			"sectionContent": // ??????
	 * }
	 * ?????????"ok"
	 * 
	 * */
	@UserLoginToken
	@GetMapping("/fetchSectionContent.do")
	public Map<String, Object> fetchSectionContent(String sectionId) {
		return controllerTool.success(repositoryService.fetchSectionContent(sectionId));
	}
	
	@UserLoginToken
	@PostMapping("/stopRepos.do")
	public Map<String, Object> stopRepos(HttpServletRequest request, String id) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(repositoryService.changeReposState(id, "0", currentUser));
	}
	
	@UserLoginToken
	@PostMapping("/startRepos.do")
	public Map<String, Object> startRepos(HttpServletRequest request, String id) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(repositoryService.changeReposState(id, "1", currentUser));
	}
	
	@UserLoginToken
	@PostMapping("/commendRepos.do")
	public Map<String, Object> commendRepos(HttpServletRequest request, String id) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(repositoryService.changeCommendState(id, "1", currentUser));
	}
	
	@UserLoginToken
	@PostMapping("/uncommendRepos.do")
	public Map<String, Object> uncommendRepos(HttpServletRequest request, String id) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		return controllerTool.success(repositoryService.changeCommendState(id, "0", currentUser));
	}
	
	/**
	 * ????????????????????????
	 * ?????????id  // ??????id
	 * {
	 *				"id": 
	 *				"reposTitle": //????????????
	 *				"catId": //??????ID
	 *				"publishDate": //????????????
	 *				"reposAuthor": //??????
	 *				"reposDigest": //????????????
	 *				"reposDrug": //??????
	 * }
	 * ????????????ok??????  "repeat"????????????????????????
	 * */
	@UserLoginToken
	@PostMapping("/saveRepos.do")
	public Map<String, Object> saveRepos(HttpServletRequest request, @RequestBody BizKnowledge entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		String res = repositoryService.saveRepos(entity, currentUser);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
	/**
	 * ?????????????????????reposId???????????????
	 * ?????????reposId??????????????????ID???
	 * ?????????
	 * {
	 * 	"data":[
	 * 		{
	 * 			"id": // ??????id
	 * 			"reposId": // ?????????id
	 * 			"sectionName": // ????????????
	 * 			"sectionLevel": // ?????????1
	 * 			"pSectionId": // ????????????id
	 * 			"sectionSort": // ??????
	 * 			"children": [ // ?????????????????????????????????level???2
	 * 				...
	 * 			]
	 * 		},
	 * 		...
	 * 	]
	 * }
	 * 
	 * */
	@UserLoginToken
	@GetMapping("/getSectionList.do")
	public Map<String, Object> getSectionList(String reposId) {
		return controllerTool.success(repositoryService.getSectionList(reposId));
	}
	
	/**
	 * ??????????????????
	 * ?????????{
	 *			"id": // ??????id
	 * 			"reposId": // ?????????id
	 * 			"sectionName": // ????????????
	 * 			"sectionLevel": // ??????
	 * 			"pSectionId": // ????????????id
	 * 			"sectionSort": // ??????
	 * 			"sectionContent": // ??????
	 * }
	 * ?????????id
	 * 
	 * */
	@UserLoginToken
	@PostMapping("/saveSection.do")
	public Map<String, Object> saveSection(HttpServletRequest request, @RequestBody BizKnowledgeContent entity) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		
		String res = repositoryService.saveSection(entity, currentUser);
		return controllerTool.success(res);
	}
	
	/**
	 * ??????????????????
	 * ?????????"sectionId" 
	 * ?????????"ok"
	 * 
	 * */
	@UserLoginToken
	@PostMapping("/deleteSection.do")
	public Map<String, Object> deleteSection(String sectionId) {
		String res = repositoryService.deleteSection(sectionId);
		return controllerTool.success(res);
	}
	
	/**
	 * ????????????????????????
	 * ?????????[{
	 *			"id": // ??????id
	 * 			"sectionSort": // ??????
	 * },...]
	 * ?????????"ok"
	 * 
	 * */
	@UserLoginToken
	@PostMapping("/saveSectionSort.do")
	public Map<String, Object> saveSectionSort(@RequestBody ReposSearchBean bean) {
		String res = repositoryService.saveSectionSort(bean.getSectionList());
		return controllerTool.success(res);
	}

	@UserLoginToken
	@GetMapping("/getSectionListSel.do")
	public Map<String, Object> getSectionListSel(String reposId) {
		return controllerTool.success(repositoryService.getSectionListSel(reposId));
	}

}
