package cn.com.metamedical.controller.repos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.repos.ReposSearchBean;
import cn.com.metamedical.model.entity.BaseKnowledgeCatagory;
import cn.com.metamedical.service.ICatagoryService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.comm.ControllerTool;

@RestController
@RequestMapping("/repos")
public class CatagoryController {
	@Autowired
	private ControllerTool controllerTool;
	
	@Autowired
	private ICatagoryService catagoryService;
	
	@UserLoginToken
	@GetMapping("/catagorylist.do")
	public Map<String, Object> catagorylist(ReposSearchBean bean) {
		IPage<Map<String, Object>> page = catagoryService.catagorylist(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@GetMapping("/allCatagorylist.do")
	public Map<String, Object> allCatagorylist() {
		return controllerTool.success(catagoryService.allCatagorylist());
	}
	
	@UserLoginToken
	@PostMapping("/stopCatagory.do")
	public Map<String, Object> stopCatagory(String id) {
		return controllerTool.success(catagoryService.changeCatagoryState(id, "0"));
	}

	@UserLoginToken
	@PostMapping("/startCatagory.do")
	public Map<String, Object> startCatagory(String id) {
		return controllerTool.success(catagoryService.changeCatagoryState(id, "1"));
	}
	
	@UserLoginToken
	@PostMapping("/saveCatagory.do")
	public Map<String, Object> saveCatagory(@RequestBody BaseKnowledgeCatagory entity) {
		String res = catagoryService.saveCatagory(entity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
}
