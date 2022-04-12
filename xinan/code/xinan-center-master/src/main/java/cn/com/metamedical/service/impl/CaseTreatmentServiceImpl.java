package cn.com.metamedical.service.impl;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.CaseTreatmentDelBean;
import cn.com.metamedical.bean.medical.CaseTreatmentSaveBean;
import cn.com.metamedical.bean.medical.MedicalCaseDelBean;
import cn.com.metamedical.bean.medical.TreatmentDrugResult;
import cn.com.metamedical.mapper.BizCaseDiseaseMapper;
import cn.com.metamedical.mapper.BizCaseDoseageMapper;
import cn.com.metamedical.mapper.BizCaseTreatmentMapper;
import cn.com.metamedical.model.entity.BizCaseDoseage;
import cn.com.metamedical.model.entity.BizCaseTreatment;
import cn.com.metamedical.service.ICaseTreatmentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@Service
public class CaseTreatmentServiceImpl implements ICaseTreatmentService {

    @Resource
    private BizCaseTreatmentMapper caseTreatmentMapper;

    @Resource
    private BizCaseDiseaseMapper caseDiseaseMapper;

    @Resource
    private BizCaseDoseageMapper caseDoseageMapper;

    @Override
    @Transactional
    public String saveCaseTreatment(UserBean userBean, CaseTreatmentSaveBean bean) {
        if (StringUtils.isNotBlank(bean.getId())){
            //修改
            BizCaseTreatment bizCaseTreatment = caseTreatmentMapper.selectById(bean.getId());
            BeanUtils.copyProperties(bean, bizCaseTreatment);
            caseTreatmentMapper.updateById(bizCaseTreatment);
        }else{
            //新增
            bean.setId(UUID.randomUUID().toString());
            BizCaseTreatment bizCaseTreatment = new BizCaseTreatment();
            BeanUtils.copyProperties(bean, bizCaseTreatment);
            bizCaseTreatment.setCreateUserId(userBean.getUserId());
            bizCaseTreatment.setCreateUserName(userBean.getRealName());
            bizCaseTreatment.setCreateTime(LocalDateTime.now());
            caseTreatmentMapper.insert(bizCaseTreatment);
        }
        //TODO 新增药品关联
        caseDoseageMapper.delete(new LambdaQueryWrapper<BizCaseDoseage>().eq(BizCaseDoseage::getDoseageId, bean.getId()));
        List<TreatmentDrugResult> record = bean.getRecord();
        for (TreatmentDrugResult treatmentDrugResult : record) {
            BizCaseDoseage caseDoseage = new BizCaseDoseage();
            caseDoseage.setDoseageId(bean.getId());
            caseDoseage.setCaseId(bean.getCaseId());
            caseDoseage.setId(UUID.randomUUID().toString());
            caseDoseage.setDrugId(treatmentDrugResult.getIndexId());
            caseDoseage.setDrugUsage(treatmentDrugResult.getCount());
            caseDoseageMapper.insert(caseDoseage);
        }

        return "ok";
    }

    @Override
    public BizCaseTreatment getCaseTreatment(UserBean currentUser, MedicalCaseDelBean bean) {
//        String caseId = bean.getId();// caseId
//        String cdId = bean.getCdId();// biz_case_disease ID
//        BizCaseTreatment bizCaseTreatment = caseTreatmentMapper.selectOne(new LambdaQueryWrapper<BizCaseTreatment>()
//                .eq(BizCaseTreatment::getCaseId, caseId).eq(BizCaseTreatment::getCaseDiseaseId, cdId));
//        BizCaseTreatment bizCaseTreatment = ;
        return caseTreatmentMapper.selectById(bean.getId());
    }

    @Override
    public List<TreatmentDrugResult> queryTreatmentDrugs(UserBean currentUser, CaseTreatmentDelBean bean) {
        List<TreatmentDrugResult> result = new ArrayList<>();
        List<Map<String, String>> resultList = caseDiseaseMapper.queryTreatmentDrugs(bean.getId(), bean.getCatName());
        for (Map<String, String> item : resultList) {
            TreatmentDrugResult drugResult = new TreatmentDrugResult();
            drugResult.setId(UUID.randomUUID().toString());
            drugResult.setCount(item.get("drug_usage"));
            drugResult.setDrugName(item.get("repos_title"));
            drugResult.setIndexId(item.get("drug_id"));
            result.add(drugResult);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryTreatmentList(UserBean currentUser, CaseTreatmentDelBean bean) {
//        List<Map<String, Object>> result = caseTreatmentMapper.queryTreatmentList(bean.getId());
//        for (Map<String, Object> map : result) {
//            String treatmentId = (String)map.get("id");
//            if (StringUtils.isBlank(treatmentId)) continue;
//            List<Map<String, String>> resultDrugs =  caseDoseageMapper.selectListByTreatmentId(treatmentId);
//            String drugInfo = resultDrugs.stream().map(drug -> drug.get("drug")).collect(Collectors.joining(", "));
//            map.put("drugInfo", drugInfo);
//        }
        return caseTreatmentMapper.queryTreatmentList(bean.getId(), bean.getCaseDiseaseId());
    }
}
