package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.SysUser;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 由于医生表的信息很少，也保存到此处 Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-10-12
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	List<Map<String, Object>> getUserMenuFuncList(String roleIds);
	
	IPage<Map<String, Object>> getUserList(IPage<Map<String, Object>> page, String userCode, String userName, String userState, String orgId, String userType);
}
