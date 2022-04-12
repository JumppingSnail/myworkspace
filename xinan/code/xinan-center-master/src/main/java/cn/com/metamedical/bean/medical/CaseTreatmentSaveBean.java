package cn.com.metamedical.bean.medical;

import cn.com.metamedical.model.entity.BizCaseTreatment;

import java.util.List;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class CaseTreatmentSaveBean extends BizCaseTreatment {

    private List<TreatmentDrugResult> record;

    public List<TreatmentDrugResult> getRecord() {
        return record;
    }

    public void setRecord(List<TreatmentDrugResult> record) {
        this.record = record;
    }
}
