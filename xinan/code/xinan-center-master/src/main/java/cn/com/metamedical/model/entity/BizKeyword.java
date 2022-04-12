package cn.com.metamedical.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Allen
 * @since 2022-02-26
 */
@TableName("biz_keyword")
public class BizKeyword implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String keywordName;

    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String KEYWORD_NAME = "keyword_name";

    public static final String REMARK = "remark";

    @Override
    public String toString() {
        return "BizKeyword{" +
        "id=" + id +
        ", keywordName=" + keywordName +
        ", remark=" + remark +
        "}";
    }
}
