package cn.com.metamedical.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.com.metamedical.bean.comm.MenuInfo;
import cn.com.metamedical.bean.comm.MenuTotalBean;
import cn.com.metamedical.bean.comm.RoleBean;
import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.mapper.BaseOrgMapper;
import cn.com.metamedical.mapper.BaseUserOrgRelMapper;
import cn.com.metamedical.mapper.SysMenuMapper;
import cn.com.metamedical.mapper.SysRoleMapper;
import cn.com.metamedical.mapper.SysUserMapper;
import cn.com.metamedical.mapper.SysUserRoleRelMapper;
import cn.com.metamedical.model.entity.BaseOrg;
import cn.com.metamedical.model.entity.BaseUserOrgRel;
import cn.com.metamedical.model.entity.SysMenu;
import cn.com.metamedical.model.entity.SysRole;
import cn.com.metamedical.model.entity.SysUser;
import cn.com.metamedical.model.entity.SysUserRoleRel;
import cn.com.metamedical.service.IAuthService;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.tool.CommonTool;
import cn.com.metamedical.util.tool.MD5Util;

@Service("authService")
public class AuthServiceImpl implements IAuthService {
	@Value("${yzmeta.login-count}")
	private int loginCount;
	
	@Autowired
	private CommonTool commonTool;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private BaseOrgMapper baseOrgMapper;
	
	@Autowired
	private BaseUserOrgRelMapper baseUserOrgRelMapper;
	
	@Autowired
	private SysUserRoleRelMapper sysUserRoleRelMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	
	/**
	 * 返回信息结构：
	 * {
	 * 	 token: 成功为生成的token
	 *   error: 如果有错为错误信息，正确为空
	 *   user:  成功为用户对象，错误为空
	 * }
	 * */
	@Override
	public Map<String, Object> login(String userCode, String passwd) {
		Map<String, Object> resMap = new HashMap<>();
		
		// 1、获取该用户的登录次数，第一次为0，如果超过指定次数则返回304，缓存有效时间为30分钟
		String countStr = JvmCacheTool.getCache(userCode + "@xinan-login");
		int count = 1;
		if (StringUtils.isNotBlank(countStr)) {
			count = Integer.parseInt(countStr);
			if (count > loginCount) {
				resMap.put("error", "304");
				return resMap; // 达到最大登录次数
			}
		}
		
		// 2、查看该用户是否存在
		List<SysUser> userList = sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("user_code", userCode));
		SysUser entity = null;
		if (userList.size() > 0) {
			entity = userList.get(0);

		} else {
			resMap.put("error", "301");
			return resMap; // 没有此用户
		}
		
		// 3、在判断用户名密码前设置次数，如果成功则清空缓存，失败则累计
		JvmCacheTool.putCache(userCode + "@xinan-login", "" + (count + 1));
		String md5Password = MD5Util.get32BitMd5EncString(passwd);
		if (!md5Password.equals(entity.getUserPasswd())) {
			resMap.put("error", "302");
			return resMap; // 密码错误
		}
		JvmCacheTool.deleteCache(userCode + "@xinan-login");
		
		// 4、判断用户状态，0-停用、1-使用中
		if("0".equals(entity.getUserState())) {
			resMap.put("error", "313");
			return resMap; // 此用户停用
		}
		
		// 5、创建用户对象
		UserBean userBean = new UserBean();
		userBean.setUserCode(userCode);
		userBean.setUserId(entity.getId());
		userBean.setRealName(entity.getUserRealName());
		userBean.setUserType(entity.getUserType());

		List<BaseUserOrgRel> relList = baseUserOrgRelMapper.selectList(new QueryWrapper<BaseUserOrgRel>().eq("user_id", entity.getId()));
		if(relList.size()>0) {
			BaseUserOrgRel relBean = relList.get(0);
			BaseOrg orgBean = baseOrgMapper.selectById(relBean.getOrgId());
			userBean.setOrgId(orgBean.getId());
			userBean.setOrgName(orgBean.getOrgName());
		}
		
		
		// 6、设置角色
		StringBuilder roleIds = new StringBuilder();
		List<SysUserRoleRel> roleList = sysUserRoleRelMapper.selectList(new QueryWrapper<SysUserRoleRel>().eq("user_id", entity.getId()));
		roleList.stream().forEach(relBean->{
			String roleId = relBean.getRoleId();
			SysRole role = sysRoleMapper.selectById(roleId);
			
			RoleBean roleBean = new RoleBean();
			roleBean.setId(role.getId());
			roleBean.setRoleCode(role.getRoleCode());
			roleBean.setRoleName(role.getRoleName());

			userBean.getRoles().add(roleBean);
			
			roleIds.append("'").append(role.getId()).append("',");
		});
		roleIds.append("'0'");

		
		// 7、设置用户菜单功能列表, 得到此用户的全部菜单以及功能按钮, 此处仅支持二级菜单树
		MenuTotalBean menuTotalBean = new MenuTotalBean();
		userBean.setMenuBean(menuTotalBean);

		Map<String, List<String>> menuFuncMap = new HashMap<>();
		userBean.setMenuFuncMap(menuFuncMap);

		List<SysMenu> menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("menu_level", 0));
		SysMenu appMenutmp = null;
		if (menuList.size() > 0) {
			appMenutmp = menuList.get(0);
		}

		final SysMenu appMenu = appMenutmp;
		if (appMenu != null) {
			Map<String, MenuInfo> menuMap = new HashMap<>();
			List<Map<String, Object>> menuFunList = sysUserMapper.getUserMenuFuncList(roleIds.toString());
			menuFunList.stream().forEach(row -> {
				String menuId = row.get("menu_id").toString();
				String parentMenuId = (row.get("p_menu_id") == null ? "" : row.get("p_menu_id").toString());

				if (!menuMap.containsKey(menuId)) {
					MenuInfo menu = new MenuInfo();
					menu.setId(menuId);
					menu.setTitle(row.get("menu_name").toString());
					menu.setIcon(row.get("menu_icon") == null ? "" : row.get("menu_icon").toString());
					menu.setHref(row.get("menu_url") == null ? "" : row.get("menu_url").toString());
					menu.setPid(parentMenuId);

					menuMap.put(menuId, menu);

					// 只有该应用下的一级菜单才加入根
					if (parentMenuId.equals(appMenu.getId())) {
						menuTotalBean.getMenuInfo().add(menu);
					}
				}

				MenuInfo pmenu = menuMap.get(parentMenuId);
				if (pmenu != null && !isChild(pmenu.getChild(), menuMap.get(menuId))) {
					pmenu.getChild().add(menuMap.get(menuId));
				}

				if (row.get("menu_url") != null && StringUtils.isNotBlank(row.get("menu_url").toString())) {
					String menuUrl = row.get("menu_url").toString();

					if (!menuFuncMap.containsKey(menuUrl)) {
						menuFuncMap.put(menuUrl, new ArrayList<String>());
					}
				}
			});
		}
		
		resMap.put("token", commonTool.genTableId());
		resMap.put("user", userBean);
		
		return resMap;
	}
	
	private boolean isChild(List<MenuInfo> children, MenuInfo one) {
		if (children == null || children.size() == 0 || one == null) {
			return false;
		}

		for (MenuInfo child : children) {
			if (child.getId().equals(one.getId())) {
				return true;
			}
		}

		return false;
	}

}
