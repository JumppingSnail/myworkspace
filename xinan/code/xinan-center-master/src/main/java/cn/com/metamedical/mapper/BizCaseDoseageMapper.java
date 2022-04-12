package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BizCaseDoseage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-12-17
 */
public interface BizCaseDoseageMapper extends BaseMapper<BizCaseDoseage> {

    List<Map<String, String>> selectListByTreatmentId(String trId);
}
