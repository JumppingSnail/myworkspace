package cn.com.metamedical.bean.medical;

import cn.com.metamedical.bean.comm.BaseBean;
import org.apache.commons.lang3.StringUtils;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public class StsSearchBean extends BaseBean {
    private String diseaseName;

    private String sex;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDiseaseNameLike(){
        if (StringUtils.isBlank(diseaseName))
            return "";
        else
            return "%" + diseaseName + "%";
    }
}
