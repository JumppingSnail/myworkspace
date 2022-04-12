package cn.com.metamedical.mapper;

import cn.com.metamedical.bean.medical.CaseTreatmentDelBean;
import cn.com.metamedical.model.entity.BizCaseDisease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-12-16
 */
@Repository
public interface BizCaseDiseaseMapper extends BaseMapper<BizCaseDisease> {

    List<Map<String, String>> selectMapByCaseId(String caseId);

    List<Map<String, String>> queryTreatmentDrugs(String doseageId, String catName);
}
