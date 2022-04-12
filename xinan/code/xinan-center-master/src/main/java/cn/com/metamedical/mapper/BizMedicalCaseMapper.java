package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BizMedicalCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface BizMedicalCaseMapper extends BaseMapper<BizMedicalCase> {

    IPage<Map<String, Object>> selectMedicalCaseList(IPage<Map<String, Object>> page, @Param("cnDisease") String cnDiseaseLike,@Param("treatName") String treatNameLike);

    Map<String, Object> selectObjById(String id);

    List<Map<String, Object>> getDiseaseCount(@Param("diseaseName") String diseaseName,@Param("sex") String sex);

    IPage<Map<String, Object>> getMedicalCaseCountList(IPage<Map<String, Object>> page, @Param("diseaseName") String diseaseName);

    IPage<Map<String, Object>> getTreatmentCountList(IPage<Map<String, Object>> page, @Param("diseaseName") String diseaseName);

    IPage<Map<String, Object>> getTreatmentDrugsCountList(IPage<Map<String, Object>> page, @Param("diseaseName") String diseaseName);

    IPage<Map<String, Object>> selectMedicalCaseListByPhone(IPage<Map<String, Object>> page, @Param("cnDisease") String cnDiseaseLike);


}
