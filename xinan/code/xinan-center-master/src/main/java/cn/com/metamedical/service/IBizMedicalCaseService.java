package cn.com.metamedical.service;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public interface IBizMedicalCaseService {
    IPage<Map<String, Object>> getMedicalCaseList(MedicalSearchBean bean);

    String saveMedicalCase(UserBean currentUser, MedicalCaseSaveBean bean);

    Map<String, Object> getMedicalCase(UserBean currentUser, MedicalCaseSaveBean bean);

    String deleteMedicalCase(MedicalCaseDelBean bean);

    Map<String, Object> getCaseInfoCount(UserBean currentUser);

    Map<String, Object> getDiseaseCount(UserBean currentUser, StsSearchBean searchBean);

    IPage<Map<String, Object>> getMedicalCaseCountList(StsSearchBean searchBean);

    IPage<Map<String, Object>> getTreatmentCountList(StsSearchBean searchBean);

    IPage<Map<String, Object>> getTreatmentDrugsCountList(StsSearchBean searchBean);

    IPage<Map<String, Object>> selectMedicalCaseListByPhone(MedicalSearchBean bean);

    Map<String, Object> selectMedicalCaseDetailsByPhone(MedicalCaseDetailsBean bean);
}
