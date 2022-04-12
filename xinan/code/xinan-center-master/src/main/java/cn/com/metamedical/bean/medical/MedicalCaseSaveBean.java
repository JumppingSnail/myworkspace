package cn.com.metamedical.bean.medical;

import cn.com.metamedical.model.entity.BizMedicalCase;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class MedicalCaseSaveBean extends BizMedicalCase {

    private String cnDiseaseIds;

    public String getCnDiseaseIds() {
        return cnDiseaseIds;
    }

    public void setCnDiseaseIds(String cnDiseaseIds) {
        this.cnDiseaseIds = cnDiseaseIds;
    }
}
