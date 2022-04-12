package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * <p>
 * 由于医生表的信息很少，也保存到此处
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userCode;

    /**
     * 从SSO获取，同步方式待定
     */
    private String userPasswd;

    private String userRealName;

    /**
     * 用户名称
     */
    private String userType;

    private String userState;

    private LocalDate createDate;

    private String remark;

    private String doctorTitle;

    private String cardNumber;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static final String ID = "id";

    public static final String USER_CODE = "user_code";

    public static final String USER_PASSWD = "user_passwd";

    public static final String USER_REAL_NAME = "user_real_name";

    public static final String USER_TYPE = "user_type";

    public static final String USER_STATE = "user_state";

    public static final String CREATE_DATE = "create_date";

    public static final String REMARK = "remark";

    public static final String DOCTOR_TITLE = "doctor_title";

    public static final String CARD_NUMBER = "card_number";

    @Override
    public String toString() {
        return "SysUser{" +
        "id=" + id +
        ", userCode=" + userCode +
        ", userPasswd=" + userPasswd +
        ", userRealName=" + userRealName +
        ", userType=" + userType +
        ", userState=" + userState +
        ", createDate=" + createDate +
        ", remark=" + remark +
        ", doctorTitle=" + doctorTitle +
        ", cardNumber=" + cardNumber +
        "}";
    }
}
