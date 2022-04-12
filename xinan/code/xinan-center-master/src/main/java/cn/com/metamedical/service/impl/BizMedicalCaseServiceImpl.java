package cn.com.metamedical.service.impl;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.*;
import cn.com.metamedical.mapper.BizCaseDiseaseMapper;
import cn.com.metamedical.mapper.BizCaseTreatmentMapper;
import cn.com.metamedical.mapper.BizMedicalCaseMapper;
import cn.com.metamedical.model.entity.BizCaseDisease;
import cn.com.metamedical.model.entity.BizMedicalCase;
import cn.com.metamedical.service.IBizMedicalCaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@Service
public class BizMedicalCaseServiceImpl implements IBizMedicalCaseService {

    private static final List<String> AGE_GROUP_LIST = Arrays.asList("0-6", "7-12", "13-17", "18-30", "31-45", "46-55", "56-64", "65-");

    @Resource
    private BizMedicalCaseMapper medicalCaseMapper;

    @Resource
    private BizCaseDiseaseMapper caseDiseaseMapper;

    @Resource
    private BizCaseTreatmentMapper caseTreatmentMapper;

    @Override
    public IPage<Map<String, Object>> getMedicalCaseList(MedicalSearchBean bean) {
        IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());
        String cnDiseaseLike = bean.getCnDiseaseLike(); //中医诊断
        String treatNameLike = bean.getTreatNameLike(); //方剂
        return medicalCaseMapper.selectMedicalCaseList(page, cnDiseaseLike, treatNameLike);
    }

    @Override
    @Transactional
    public String saveMedicalCase(UserBean currentUser, MedicalCaseSaveBean bean) {
        if (StringUtils.isNotBlank(bean.getId())) {
            BizMedicalCase bizMedicalCase = medicalCaseMapper.selectById(bean.getId());
            BeanUtils.copyProperties(bean, bizMedicalCase);
            bizMedicalCase.setUpdateTime(LocalDateTime.now());
            medicalCaseMapper.updateById(bizMedicalCase);
        } else {
            bean.setId(UUID.randomUUID().toString());
            BizMedicalCase bizMedicalCase = new BizMedicalCase();
            BeanUtils.copyProperties(bean, bizMedicalCase);
            bizMedicalCase.setCreateUserId(currentUser.getUserId());
            bizMedicalCase.setCreateTime(LocalDateTime.now());
            bizMedicalCase.setCreateUserName(currentUser.getRealName());
            medicalCaseMapper.insert(bizMedicalCase);
        }
        List<BizCaseDisease> newBizCaseDiseases = new ArrayList<>();
        List<BizCaseDisease> oldBizCaseDiseases = caseDiseaseMapper.selectList(new LambdaQueryWrapper<BizCaseDisease>().eq(BizCaseDisease::getCaseId, bean.getId()));

        String cnDiseaseIds = bean.getCnDiseaseIds();
        if (StringUtils.isNotBlank(cnDiseaseIds)) {
            String[] split = cnDiseaseIds.split(",");
            for (String diseaseId : split) {
                BizCaseDisease bizCaseDisease = new BizCaseDisease();
                bizCaseDisease.setId(UUID.randomUUID().toString());
                bizCaseDisease.setCaseId(bean.getId());
                bizCaseDisease.setReposId(diseaseId);
                newBizCaseDiseases.add(bizCaseDisease);
            }
        }
        List<BizCaseDisease> oldCollect = oldBizCaseDiseases.stream().filter(item -> newBizCaseDiseases.contains(item)).collect(Collectors.toList());  //保持原样旧的不可以做任何修改的
        List<BizCaseDisease> collect = newBizCaseDiseases.stream().filter(item -> !oldBizCaseDiseases.contains(item)).collect(Collectors.toList());
        caseDiseaseMapper.delete(new LambdaQueryWrapper<BizCaseDisease>().eq(BizCaseDisease::getCaseId, bean.getId()));
        collect.addAll(oldCollect);
        for (BizCaseDisease bizCaseDisease : collect) {
            caseDiseaseMapper.insert(bizCaseDisease);
        }

        return "ok";
    }

    @Override
    public Map<String, Object> getMedicalCase(UserBean currentUser, MedicalCaseSaveBean bean) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> bizMedicalCase = medicalCaseMapper.selectObjById(bean.getId());
        resultMap.put("info", bizMedicalCase);
        List<Map<String, String>> diseaseMaps = caseDiseaseMapper.selectMapByCaseId(bean.getId());
        String reposIds = diseaseMaps.stream().map(map -> map.get("repos_id")).collect(joining(","));
        String reposTitles = diseaseMaps.stream().map(map -> map.get("repos_title")).collect(joining(","));
        resultMap.put("cnDiseaseIds", reposIds);
        resultMap.put("cnDiseaseMap", reposTitles);
        return resultMap;
    }

    @Override
    @Transactional
    public String deleteMedicalCase(MedicalCaseDelBean bean) {
        int count = caseDiseaseMapper.selectCount(new LambdaQueryWrapper<BizCaseDisease>()
                .eq(BizCaseDisease::getCaseId, bean.getId()));
        if (count <= 1) medicalCaseMapper.deleteById(bean.getId());
        BizCaseDisease bizCaseDisease = caseDiseaseMapper.selectById(bean.getCdId());
        if (Objects.isNull(bizCaseDisease)) return "404";
        caseDiseaseMapper.deleteById(bean.getCdId());

        return "ok";
    }

    @Override
    public Map<String, Object> getCaseInfoCount(UserBean currentUser) {
        Map<String, Object> resultMap = new HashMap<>();
        List<BizMedicalCase> bizMedicalCases = medicalCaseMapper.selectList(new LambdaQueryWrapper<>());
        long countMenValue = bizMedicalCases.stream().filter(medicalCase -> medicalCase.getPatientSex().equals("0")).count();
        long countWomenValue = bizMedicalCases.stream().filter(medicalCase -> medicalCase.getPatientSex().equals("1")).count();
        long countOtherValue = bizMedicalCases.stream().filter(medicalCase -> medicalCase.getPatientSex().equals("2")).count();
        resultMap.put("totalCount", bizMedicalCases.size());
        resultMap.put("menCount", countMenValue);
        resultMap.put("womenCount", countWomenValue);
        resultMap.put("otherCount", countOtherValue);
        return resultMap;
    }

    @Override
    public Map<String, Object> getDiseaseCount(UserBean currentUser, StsSearchBean searchBean) {
        Map<String, Object> resultMap = new HashMap<>();
        List<DiseaseCountResource> countResources = new ArrayList<>();

        String diseaseName = searchBean.getDiseaseName();
        String sex = searchBean.getSex();
        List<Map<String, Object>> resultData = medicalCaseMapper.getDiseaseCount(diseaseName, sex);

        for (String ageGroup : AGE_GROUP_LIST) {
            DiseaseCountResource countResource = new DiseaseCountResource();
            String[] split = ageGroup.split("-");
            long count;
            if (split.length > 1) count = resultData.stream().filter(medicalCase -> predicate(split[0], split[1]).test(medicalCase)).count();
            else count = resultData.stream().filter(medicalCase -> predicate(split[0], null).test(medicalCase)).count();

            if (ageGroup.equals("65-")) countResource.setName("65岁以上");
            else countResource.setName(ageGroup+"岁");

            countResource.setValue(count);
            countResources.add(countResource);
        }

        resultMap.put("result", "ok");
        resultMap.put("data", countResources);
        resultMap.put("total", resultData.size());

        return resultMap;
    }

    private Predicate<Map<String, Object>> predicate(String begin, String end) {
        if (StringUtils.isBlank(end))
            return medicalCase -> (int)medicalCase.get("patient_age") >= Integer.parseInt(begin);

        return medicalCase -> (int)medicalCase.get("patient_age") >= Integer.parseInt(begin)
                && (int)medicalCase.get("patient_age") <= Integer.parseInt(end);
    }

    @Override
    public IPage<Map<String, Object>> getMedicalCaseCountList(StsSearchBean bean) {
        IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());
        String diseaseName = bean.getDiseaseNameLike();
        return medicalCaseMapper.getMedicalCaseCountList(page, diseaseName);
    }

    @Override
    public IPage<Map<String, Object>> getTreatmentCountList(StsSearchBean bean) {
        IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());
        String diseaseName = bean.getDiseaseName();
        return medicalCaseMapper.getTreatmentCountList(page, diseaseName);
    }

    @Override
    public IPage<Map<String, Object>> getTreatmentDrugsCountList(StsSearchBean bean) {
        IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());
        String diseaseName = bean.getDiseaseName();
        return medicalCaseMapper.getTreatmentDrugsCountList(page, diseaseName);
    }

    @Override
    public IPage<Map<String, Object>> selectMedicalCaseListByPhone(MedicalSearchBean bean) {
        IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());
        String cnDiseaseLike = bean.getFullContentSearchLike(); //中医诊断
        return medicalCaseMapper.selectMedicalCaseListByPhone(page, cnDiseaseLike);
    }

    @Override
    public Map<String, Object> selectMedicalCaseDetailsByPhone(MedicalCaseDetailsBean bean) {
        Map<String, Object> resultMap = new HashMap<>();
        //MEDICAL 部分
        Map<String, Object> bizMedicalCase = medicalCaseMapper.selectObjById(bean.getCaseId());
        resultMap.put("info", bizMedicalCase);
        List<Map<String, String>> diseaseMaps = caseDiseaseMapper.selectMapByCaseId(bean.getCaseId());
        String reposTitles = diseaseMaps.stream().map(map -> map.get("repos_title")).collect(joining(","));
        resultMap.put("cnDiseaseMap", reposTitles);
        //处方部分
        List<Map<String, Object>> treatmentList = caseTreatmentMapper.queryTreatmentListByPhone(bean.getCaseId());
        resultMap.put("treatmentList", treatmentList);


        return resultMap;

    }
}
