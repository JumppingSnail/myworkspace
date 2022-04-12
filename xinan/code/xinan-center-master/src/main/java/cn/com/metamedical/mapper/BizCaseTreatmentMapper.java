package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BizCaseTreatment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-12-14
 */
public interface BizCaseTreatmentMapper extends BaseMapper<BizCaseTreatment> {

    List<Map<String, Object>> queryTreatmentList(String caseId, String caseDiseaseId);

    List<Map<String, Object>> queryTreatmentListByPhone(String caseId);
}
