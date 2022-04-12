package cn.com.metamedical.service;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.CaseTreatmentDelBean;
import cn.com.metamedical.bean.medical.CaseTreatmentSaveBean;
import cn.com.metamedical.bean.medical.MedicalCaseDelBean;
import cn.com.metamedical.bean.medical.TreatmentDrugResult;
import cn.com.metamedical.model.entity.BizCaseTreatment;

import java.util.List;
import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public interface ICaseTreatmentService {
    String saveCaseTreatment(UserBean userBean, CaseTreatmentSaveBean bean);

    List<TreatmentDrugResult> queryTreatmentDrugs(UserBean currentUser, CaseTreatmentDelBean bean);

    BizCaseTreatment getCaseTreatment(UserBean currentUser, MedicalCaseDelBean bean);

    List<Map<String, Object>> queryTreatmentList(UserBean currentUser, CaseTreatmentDelBean bean);
}
