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
@TableName("biz_keyword_rel")
public class BizKeywordRel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String keywordId;

    private String sourceId;

    private String sourceTbname;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceTbname() {
        return sourceTbname;
    }

    public void setSourceTbname(String sourceTbname) {
        this.sourceTbname = sourceTbname;
    }

    public static final String ID = "id";

    public static final String KEYWORD_ID = "keyword_id";

    public static final String SOURCE_ID = "source_id";

    public static final String SOURCE_TBNAME = "source_tbname";

    @Override
    public String toString() {
        return "BizKeywordRel{" +
        "id=" + id +
        ", keywordId=" + keywordId +
        ", sourceId=" + sourceId +
        ", sourceTbname=" + sourceTbname +
        "}";
    }
}
