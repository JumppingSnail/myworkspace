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
	 * 得到文章详细信息
	 * 参数：id  // 文章id
	 * 返回：
	 * {
	 *			"reposBean": {
	 *				"id": 
	 *				"reposTitle": //文章题目
	 *				"catId": //类别ID
	 *				"publishDate": //出版日期
	 *				"reposAuthor": //作者
	 *				"reposDigest": //文章摘要
	 *				"reposDrug": //药品
	 *			}
	 * 			"sectionList": [
	 * 			{
	 * 				"sectionId":
	 * 				"sectionName":
	 * 				"childSectionList": [...]
	 * 			},...
	 * 			]
	 * 			"sectionName": // 章节名称
	 * 			"sectionContent": // 内容
	 * }
	 * 
	 * */
	@UserLoginToken
	@GetMapping("/fetchRepositoryInfo.do")
	public Map<String, Object> fetchRepositoryInfo(String id) {
		return controllerTool.success(repositoryService.fetchRepositoryInfo(id));
	}
	
	/**
	 * 得到章节详细信息
	 * 参数：sectionId  章节id
	 * {
	 *			"id": // 章节id
	 * 			"reposId": // 知识库id
	 * 			"sectionName": // 章节名称
	 * 			"sectionContent": // 内容
	 * }
	 * 返回："ok"
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
	 * 保存文章详细信息
	 * 参数：id  // 文章id
	 * {
	 *				"id": 
	 *				"reposTitle": //文章题目
	 *				"catId": //类别ID
	 *				"publishDate": //出版日期
	 *				"reposAuthor": //作者
	 *				"reposDigest": //文章摘要
	 *				"reposDrug": //药品
	 * }
	 * 返回：“ok”，  "repeat"表示文章标题重复
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
	 * 得到指定知识库reposId的章节目录
	 * 参数：reposId（知识库文章ID）
	 * 返回：
	 * {
	 * 	"data":[
	 * 		{
	 * 			"id": // 章节id
	 * 			"reposId": // 知识库id
	 * 			"sectionName": // 章节名称
	 * 			"sectionLevel": // 级别，1
	 * 			"pSectionId": // 父级章节id
	 * 			"sectionSort": // 排序
	 * 			"children": [ // 子级，结构与父级相同，level为2
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
	 * 保存章节信息
	 * 参数：{
	 *			"id": // 章节id
	 * 			"reposId": // 知识库id
	 * 			"sectionName": // 章节名称
	 * 			"sectionLevel": // 级别
	 * 			"pSectionId": // 父级章节id
	 * 			"sectionSort": // 排序
	 * 			"sectionContent": // 内容
	 * }
	 * 返回：id
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
	 * 删除章节信息
	 * 参数："sectionId" 
	 * 返回："ok"
	 * 
	 * */
	@UserLoginToken
	@PostMapping("/deleteSection.do")
	public Map<String, Object> deleteSection(String sectionId) {
		String res = repositoryService.deleteSection(sectionId);
		return controllerTool.success(res);
	}
	
	/**
	 * 保存章节排序信息
	 * 参数：[{
	 *			"id": // 章节id
	 * 			"sectionSort": // 排序
	 * },...]
	 * 返回："ok"
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
