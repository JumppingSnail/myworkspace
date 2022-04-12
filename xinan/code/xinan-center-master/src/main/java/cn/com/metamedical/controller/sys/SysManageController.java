package cn.com.metamedical.controller.sys;

import java.util.HashMap;
import java.util.List;
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
import cn.com.metamedical.bean.sys.RoleVoBean;
import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.bean.sys.UserVoBean;
import cn.com.metamedical.model.entity.SysRole;
import cn.com.metamedical.model.entity.SysUser;
import cn.com.metamedical.service.ISysManageService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;

@RestController
@RequestMapping("/sys")
public class SysManageController {
	@Autowired
	private ControllerTool controllerTool;

	@Autowired
	private ISysManageService sysManageService;

	@UserLoginToken
	@GetMapping("/userlist.do")
	public Map<String, Object> getUserList(SysSearchBean bean) {
		IPage<Map<String, Object>> page = sysManageService.getUserList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}

	@UserLoginToken
	@GetMapping("/getAllDoctors.do")
	public Map<String, Object> getAllDoctors(SysSearchBean bean) {
		List<SysUser> result = sysManageService.getAllDoctors(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count",0);
		resMap.put("data", result);
		return resMap;
	}

	@UserLoginToken
	@PostMapping("/delUser.do")
	public Map<String, Object> deleteUser(String id) {
		return controllerTool.success(sysManageService.deleteUser(id));
	}

	@UserLoginToken
	@PostMapping("/stopUser.do")
	public Map<String, Object> stopUser(String id) {
		return controllerTool.success(sysManageService.chageUserState(id, "0"));
	}

	@UserLoginToken
	@PostMapping("/startUser.do")
	public Map<String, Object> startUser(String id) {
		return controllerTool.success(sysManageService.chageUserState(id, "1"));
	}

	@UserLoginToken
	@PostMapping("/resetUserPwd.do")
	public Map<String, Object> resetUserPwd(String id) {
		return controllerTool.success(sysManageService.resetUserPwd(id));
	}

	@UserLoginToken
	@PostMapping("/saveUser.do")
	public Map<String, Object> saveUser(@RequestBody SysUser entity) {
		String res = sysManageService.saveUser(entity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
	@UserLoginToken
	@PostMapping("/saveRole.do")
	public Map<String, Object> saveRole(@RequestBody SysRole entity) {
		String res = sysManageService.saveRole(entity);
		if(!"ok".equals(res)) {
			return controllerTool.error(res, res);
		}
		
		return controllerTool.success("ok");
	}
	
	@UserLoginToken
	@GetMapping("/fetchCurrentUserInfo.do")
	public Map<String, Object> fetchCurrentUserInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		return controllerTool.responseToClient(currentUser);
	}

	@UserLoginToken
	@PostMapping("/saveCurrentUser.do")
	public Map<String, Object> saveCurrentUser(HttpServletRequest request, @RequestBody SysUser entity) {
		String result = sysManageService.saveCurrentUser(entity);

		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);
		currentUser.setRealName(entity.getUserRealName());
		JvmCacheTool.putTokenInfo(token, currentUser);

		return controllerTool.success(result);
	}

	@UserLoginToken
	@PostMapping("/changeCurrentUserPassword.do")
	public Map<String, Object> changeCurrentUserPassword(HttpServletRequest request, String oldPassword,
														 String newPassword) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean currentUser = JvmCacheTool.getTokenInfo(token);

		String result = sysManageService.changeCurrentUserPassword(currentUser, currentUser.getUserId(),
				oldPassword, newPassword);
		return controllerTool.responseToClient(result, result);
	}

	@UserLoginToken
	@GetMapping("/allRoleList.do")
	public Map<String, Object> getAllRoleList() {
		return controllerTool.success(sysManageService.getAllRoleList());
	}
	
	@UserLoginToken
	@PostMapping("/saveUserRole.do")
	public Map<String, Object> saveUserRole(@RequestBody UserVoBean userVo) {
		return controllerTool
				.success(sysManageService.saveUserRole(userVo.getUserId(), userVo.getRoleList()));
	}
	
	@UserLoginToken
	@GetMapping("/getAllOrgList.do")
	public Map<String, Object> getAllOrgList(SysSearchBean bean) {
		IPage<Map<String, Object>> page = sysManageService.getAllOrgList(bean);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("code", 0);
		resMap.put("msg", "");
		resMap.put("count", page.getTotal());
		resMap.put("data", page.getRecords());
		return resMap;
	}
	
	@UserLoginToken
	@PostMapping("/saveUserOrg.do")
	public Map<String, Object> saveUserOrg(@RequestBody UserVoBean userVo) {
		return controllerTool
				.success(sysManageService.saveUserOrg(userVo.getUserId(), userVo.getOrgId()));
	}
	
	@UserLoginToken
	@GetMapping("/roleMenuList.do")
	public Map<String, Object> getRoleMenuList(String roleId) {
		return controllerTool.success(sysManageService.getRoleMenuList(roleId));
	}
	
	@UserLoginToken
	@PostMapping("/saveRoleMenu.do")
	public Map<String, Object> saveRoleMenu(@RequestBody RoleVoBean roleVo) {
		return controllerTool
				.success(sysManageService.saveRoleMenu(roleVo.getRoleId(), roleVo.getMenuList()));
	}
}
