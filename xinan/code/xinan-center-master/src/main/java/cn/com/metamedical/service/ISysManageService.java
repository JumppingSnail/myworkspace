package cn.com.metamedical.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.bean.sys.TreeBean;
import cn.com.metamedical.model.entity.SysRole;
import cn.com.metamedical.model.entity.SysUser;

public interface ISysManageService {
	public IPage<Map<String, Object>> getUserList(SysSearchBean searchBean);
	public IPage<Map<String, Object>> getAllOrgList(SysSearchBean searchBean);

	public String deleteUser(String id);

	public String chageUserState(String id, String newState);

	public String resetUserPwd(String id);
	
	public String saveUser(SysUser entity);
	public String saveRole(SysRole entity);
	
	public List<Map<String, Object>> getAllRoleList();
	
	public String saveUserRole(String userId, List<String> roleList);
	public String saveUserOrg(String userId, String orgId);
	
	public List<TreeBean> getRoleMenuList(String roleId);
	
	public String saveRoleMenu(String roleId, List<TreeBean> menuList);

	String saveCurrentUser(SysUser entity);

	String changeCurrentUserPassword(UserBean currentUser, String userId, String oldPassword, String newPassword);

    List<SysUser> getAllDoctors(SysSearchBean bean);
}
