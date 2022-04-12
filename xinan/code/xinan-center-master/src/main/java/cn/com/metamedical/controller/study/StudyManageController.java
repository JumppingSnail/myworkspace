package cn.com.metamedical.controller.study;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.study.StudySearchBean;
import cn.com.metamedical.model.entity.BizDiseaseCategory;
import cn.com.metamedical.model.entity.BizKeyword;
import cn.com.metamedical.service.IStudyManageService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.comm.ControllerTool;

@RestController
@RequestMapping("/study")
public class StudyManageController {
	@Autowired
	private ControllerTool controllerTool;
	
	@Autowired
	private IStudyManageService studyManageService;
	
	@UserLoginToken
	@GetMapping("/subjectList.do")
	public Map<String, Object> subjectList(StudySearchBean bean) {
		IPage<Map<String, Object>> page = studyManageService.subjectList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@GetMapping("/keywordList.do")
	public Map<String, Object> keywordList(StudySearchBean bean) {
		IPage<Map<String, Object>> page = studyManageService.keywordList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@GetMapping("/linkedKeywordList.do")
	public Map<String, Object> linkedKeywordList(StudySearchBean bean) {
		IPage<Map<String, Object>> page = studyManageService.linkedKeywordList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@GetMapping("/unlinkedKeywordList.do")
	public Map<String, Object> unlinkedKeywordList(StudySearchBean bean) {
		IPage<Map<String, Object>> page = studyManageService.unlinkedKeywordList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@PostMapping("/saveKeywordRel.do")
	public Map<String, Object> saveKeywordRel(@RequestBody StudySearchBean beanEntity) {
		String res = studyManageService.saveKeywordRel(beanEntity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
	@UserLoginToken
	@PostMapping("/deleteLinkedKeyword.do")
	public Map<String, Object> deleteLinkedKeyword(String id) {
		return controllerTool.success(studyManageService.deleteLinkedKeyword(id));
	}
	
	@UserLoginToken
	@PostMapping("/deleteSubject.do")
	public Map<String, Object> deleteSubject(String id) {
		return controllerTool.success(studyManageService.deleteSubject(id));
	}
	
	@UserLoginToken
	@PostMapping("/deleteKeyword.do")
	public Map<String, Object> deleteKeyword(String id) {
		return controllerTool.success(studyManageService.deleteKeyword(id));
	}
	
	@UserLoginToken
	@PostMapping("/saveSubject.do")
	public Map<String, Object> saveSubject(@RequestBody BizDiseaseCategory entity) {
		String res = studyManageService.saveSubject(entity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
	@UserLoginToken
	@PostMapping("/saveKeyword.do")
	public Map<String, Object> saveKeyword(@RequestBody BizKeyword entity) {
		String res = studyManageService.saveKeyword(entity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
}
