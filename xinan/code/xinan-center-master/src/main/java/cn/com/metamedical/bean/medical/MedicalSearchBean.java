package cn.com.metamedical.bean.medical;

import cn.com.metamedical.bean.comm.BaseBean;
import org.apache.commons.lang3.StringUtils;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class MedicalSearchBean extends BaseBean {

    private String cnDisease;

    private String treatName;

    private String caseDescription;

    private String fullContentSearch;

    public String getCnDisease() {
        return cnDisease;
    }

    public void setCnDisease(String cnDisease) {
        this.cnDisease = cnDisease;
    }

    public String getTreatName() {
        return treatName;
    }

    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    public String getCnDiseaseLike(){
        if (StringUtils.isNotBlank(cnDisease)){
            return "%" + cnDisease + "%";
        }
        return "";
    }

    public String getTreatNameLike(){
        if (StringUtils.isNotBlank(treatName)){
            return "%" + treatName + "%";
        }
        return "";
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getCaseDescriptionLike(){
        if (StringUtils.isNotBlank(caseDescription)){
            return "%" + caseDescription + "%";
        }
        return "";
    }

    public String getFullContentSearch() {
        return fullContentSearch;
    }

    public String getFullContentSearchLike() {
        if (StringUtils.isNotBlank(fullContentSearch)){
            return "%" + fullContentSearch + "%";
        }
        return "";
    }

    public void setFullContentSearch(String fullContentSearch) {
        this.fullContentSearch = fullContentSearch;
    }


}
