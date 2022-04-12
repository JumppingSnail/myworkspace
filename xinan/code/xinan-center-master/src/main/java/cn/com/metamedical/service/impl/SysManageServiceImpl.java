package cn.com.metamedical.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.bean.sys.TreeBean;
import cn.com.metamedical.mapper.BaseOrgMapper;
import cn.com.metamedical.mapper.BaseUserOrgRelMapper;
import cn.com.metamedical.mapper.SysMenuMapper;
import cn.com.metamedical.mapper.SysRoleMapper;
import cn.com.metamedical.mapper.SysRoleMenuRelMapper;
import cn.com.metamedical.mapper.SysUserMapper;
import cn.com.metamedical.mapper.SysUserRoleRelMapper;
import cn.com.metamedical.model.entity.BaseOrg;
import cn.com.metamedical.model.entity.BaseUserOrgRel;
import cn.com.metamedical.model.entity.SysMenu;
import cn.com.metamedical.model.entity.SysRole;
import cn.com.metamedical.model.entity.SysRoleMenuRel;
import cn.com.metamedical.model.entity.SysUser;
import cn.com.metamedical.model.entity.SysUserRoleRel;
import cn.com.metamedical.service.ISysManageService;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.tool.CommonTool;
import cn.com.metamedical.util.tool.MD5Util;

@Service("sysManageService")
public class SysManageServiceImpl implements ISysManageService {
	@Value("${yzmeta.default-passwd}")
	private String defaultPasswd;
	
	@Value("${yzmeta.login-count}")
	private int loginCount;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRoleRelMapper sysUserRoleRelMapper;

	@Autowired
	private BaseUserOrgRelMapper baseUserOrgRelMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private BaseOrgMapper baseOrgMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private SysRoleMenuRelMapper sysRoleMenuRelMapper;

	@Autowired
	private CommonTool commonTool;

	@Override
	public IPage<Map<String, Object>> getUserList(SysSearchBean searchBean) {
		// 处理框架中处理left join时自动优化所报的警告
		IPage<Map<String, Object>> page = new Page<Map<String, Object>>(searchBean.getPage(), searchBean.getLimit())
				.setOptimizeCountSql(false);
		IPage<Map<String, Object>> resPage =  sysUserMapper.getUserList(page, searchBean.getUserCode4Like(), searchBean.getUserName4Like(),
				searchBean.getUserStateSearch(), searchBean.getOrgIdSearch(), searchBean.getUserTypeSearch());
		
		resPage.getRecords().stream().forEach(row -> {
			String userCode = row.get("user_code").toString();
			String countStr = JvmCacheTool.getCache(userCode + "@xinan-login");
			if (StringUtils.isNotBlank(countStr)) {
				if (Integer.parseInt(countStr) > loginCount) {
					row.put("lock_status", "锁定");
				} else {
					row.put("lock_status", "正常");
				}
			} else {
				row.put("lock_status", "正常");
			}

		});
		
		return resPage;
	}

	@Override
	public IPage<Map<String, Object>> getAllOrgList(SysSearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
		return baseOrgMapper.selectMapsPage(page, new QueryWrapper<BaseOrg>().orderByAsc("org_name"));
	}

	@Override
	public String saveUser(SysUser entity) {
		String userId = entity.getId();

		if (StringUtils.isBlank(entity.getId())) {
			if (sysUserMapper.selectCount(new QueryWrapper<SysUser>().eq("user_code", entity.getUserCode())) > 0) {
				return "310";
			}

			String userCode = entity.getUserCode();

			userId = commonTool.genTableId();
			entity.setId(userId);
			entity.setCreateDate(LocalDate.now());
			entity.setUserPasswd(MD5Util.get32BitMd5EncString(userCode.substring(userCode.length() - 6)));
			entity.setUserState("1");
			sysUserMapper.insert(entity);

		} else {
			if (sysUserMapper.selectCount(
					new QueryWrapper<SysUser>().eq("user_code", entity.getUserCode()).ne("id", entity.getId())) > 0) {
				return "310";
			}

			sysUserMapper.updateById(entity);
		}

		return "ok";
	}

	@Override
	@Transactional
	public String saveRoleMenu(String roleId, List<TreeBean> menuList) {
		if (StringUtils.isBlank(roleId)) {
			return "empty";
		}

		sysRoleMenuRelMapper.delete(new QueryWrapper<SysRoleMenuRel>().eq("role_id", roleId));
		List<String> menuIdList = new ArrayList<>();
		menuList.stream().forEach(treeBean -> {
			menuIdList.add(treeBean.getId());
			treeBean.getChildren().stream().forEach(child -> {
				menuIdList.add(child.getId());
				child.getChildren().stream().forEach(grandChild -> {
					menuIdList.add(grandChild.getId());
				});
			});
		});

		menuIdList.forEach(menuId -> {
			SysRoleMenuRel relEntity = new SysRoleMenuRel();
			relEntity.setId(commonTool.genTableId());
			relEntity.setRoleId(roleId);
			relEntity.setMenuId(menuId);
			sysRoleMenuRelMapper.insert(relEntity);
		});

		return "ok";
	}

	@Override
	public List<TreeBean> getRoleMenuList(String roleId) {
		List<SysMenu> allMenuList = sysMenuMapper
				.selectList(new QueryWrapper<SysMenu>().orderByAsc("menu_level", "menu_sort"));

		Set<String> roleMenuIdList = sysRoleMenuRelMapper
				.selectList(new QueryWrapper<SysRoleMenuRel>().eq("role_id", roleId)).stream()
				.map(row -> row.getMenuId()).collect(Collectors.toSet());

		List<TreeBean> menuList = new ArrayList<>();

		Map<String, TreeBean> topLevelMap = new HashMap<>();
		for (SysMenu menu : allMenuList) {
			if (menu.getMenuLevel() == 0) {
				TreeBean node = new TreeBean();
				node.setId(menu.getId());
				node.setTitle(
						menu.getMenuName() + (StringUtils.isNotBlank(menu.getRemark()) ? "：" + menu.getRemark() : ""));
				menuList.add(node);
				topLevelMap.put(menu.getId(), node);

			} else if (menu.getMenuLevel() == 1 || menu.getMenuLevel() == 2) {
				TreeBean parentNode = topLevelMap.get(menu.getpMenuId());
				if (parentNode != null) {
					TreeBean childNode = new TreeBean();
					childNode.setId(menu.getId());
					childNode.setTitle(menu.getMenuName()
							+ (StringUtils.isNotBlank(menu.getRemark()) ? "：" + menu.getRemark() : ""));
					if (roleMenuIdList.contains(menu.getId()) && menu.getMenuLevel() == 2) {
						childNode.setChecked(true);
					}
					parentNode.getChildren().add(childNode);
					topLevelMap.put(menu.getId(), childNode);
				}
			}
		}

		return menuList;
	}

	@Override
	public String saveRole(SysRole entity) {
		if (StringUtils.isBlank(entity.getId())) {
			if (sysRoleMapper.selectCount(new QueryWrapper<SysRole>().eq("role_name", entity.getRoleName())) > 0) {
				return "310";
			}

			entity.setId(commonTool.genTableId());
			sysRoleMapper.insert(entity);

		} else {
			if (sysRoleMapper.selectCount(
					new QueryWrapper<SysRole>().eq("role_name", entity.getRoleName()).ne("id", entity.getId())) > 0) {
				return "310";
			}

			sysRoleMapper.updateById(entity);
		}

		return "ok";
	}

	@Override
	public List<Map<String, Object>> getAllRoleList() {
		QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByAsc("role_type", "role_name");
		return sysRoleMapper.selectMaps(queryWrapper);
	}

	@Override
	@Transactional
	public String saveUserRole(String userId, List<String> roleList) {
		if (StringUtils.isBlank(userId)) {
			return "empty";
		}

		sysUserRoleRelMapper.delete(new QueryWrapper<SysUserRoleRel>().eq("user_id", userId));
		roleList.stream().forEach(roleId -> {
			SysUserRoleRel relEntity = new SysUserRoleRel();
			relEntity.setId(commonTool.genTableId());
			relEntity.setUserId(userId);
			relEntity.setRoleId(roleId);
			sysUserRoleRelMapper.insert(relEntity);
		});

		return "ok";
	}

	@Transactional
	@Override
	public String saveUserOrg(String userId, String orgId) {
		baseUserOrgRelMapper.delete(new QueryWrapper<BaseUserOrgRel>().eq("user_id", userId));

		BaseUserOrgRel entity = new BaseUserOrgRel();
		entity.setId(commonTool.genTableId());
		entity.setUserId(userId);
		entity.setOrgId(orgId);
		baseUserOrgRelMapper.insert(entity);

		return "ok";
	}

	@Override
	public String deleteUser(String id) {
		if (StringUtils.isNotBlank(id)) {
			sysUserMapper.deleteById(id);
			sysUserRoleRelMapper.delete(new QueryWrapper<SysUserRoleRel>().eq("user_id", id));
			baseUserOrgRelMapper.delete(new QueryWrapper<BaseUserOrgRel>().eq("user_id", id));
		}
		return "ok";
	}

	@Override
	public String chageUserState(String id, String newState) {
		if (StringUtils.isNotBlank(id)) {
			SysUser entity = new SysUser();
			entity.setId(id);
			entity.setUserState(newState);
			sysUserMapper.updateById(entity);
		}
		return "ok";
	}

	@Override
	public String resetUserPwd(String id) {
		if (StringUtils.isNotBlank(id)) {
			SysUser entity = new SysUser();
			entity.setId(id);
			entity.setUserPasswd(MD5Util.get32BitMd5EncString(defaultPasswd.trim()));
			sysUserMapper.updateById(entity);
		}
		return "ok";
	}

	@Override
	public String saveCurrentUser(SysUser entity) {
		if (StringUtils.isNotBlank(entity.getId())) {
			SysUser sysUser = sysUserMapper.selectById(entity.getId());
			if (Objects.isNull(sysUser))
				return "404";
			BeanUtils.copyProperties(entity, sysUser);
			sysUserMapper.updateById(entity);
		} else {
			entity.setId(commonTool.genTableId());
			entity.setCreateDate(LocalDate.now());
			sysUserMapper.insert(entity);
		}

		return "ok";
	}

	@Override
	public String changeCurrentUserPassword(UserBean currentUser, String userId, String oldPassword,
			String newPassword) {
		if (!isStrongPasswd(newPassword)) {
			return "weak";
		}

		SysUser user = sysUserMapper.selectById(userId);
		String md5OldPass = MD5Util.get32BitMd5EncString(oldPassword);
		String md5NewPass = MD5Util.get32BitMd5EncString(newPassword);
		if (!user.getUserPasswd().equals(md5OldPass)) {
			return "314";
		}

		user.setUserPasswd(md5NewPass);
		sysUserMapper.updateById(user);

		return "ok";

	}

	private boolean isStrongPasswd(String password) {
		if (StringUtils.isBlank(password) || password.length() < 8) {
			return false;
		}

		int count = 0;
		if (password.matches(".*[a-z]{1,}.*")) {
			count++;
		}
		if (password.matches(".*[A-Z]{1,}.*")) {
			count++;
		}
		if (password.matches(".*\\d{1,}.*")) {
			count++;
		}
		if (password.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
			count++;
		}

		return count > 2;
	}

	@Override
	public List<SysUser> getAllDoctors(SysSearchBean bean) {
		LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();
		lambdaQueryWrapper.eq(SysUser::getUserType, "3");
		lambdaQueryWrapper.eq(SysUser::getUserState, "1");
		lambdaQueryWrapper.orderByDesc(SysUser::getCreateDate);
		return sysUserMapper.selectList(lambdaQueryWrapper);
	}
}
