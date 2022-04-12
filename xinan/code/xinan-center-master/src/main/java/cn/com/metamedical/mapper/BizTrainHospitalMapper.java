package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BaseOrg;
import cn.com.metamedical.model.entity.BizTrainHospital;
import cn.com.metamedical.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 记录该培训所涉及的医院 Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
public interface BizTrainHospitalMapper extends BaseMapper<BizTrainHospital> {

    List<BaseOrg> selectOrgListByTrainId(@Param("trainId") String trainId);

    List<SysUser> selectUserListByTrainId(@Param("trainId") String trainId);
}
