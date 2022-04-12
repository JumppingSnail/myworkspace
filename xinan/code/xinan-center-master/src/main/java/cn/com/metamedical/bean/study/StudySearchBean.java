package cn.com.metamedical.bean.study;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.metamedical.bean.comm.BaseBean;

public class StudySearchBean extends BaseBean {
	private String subjectNameSearch;
	private String keywordSearch;
	private String sourceId;
	private String sourceTbname;
	private List<String> keywordIdList = new ArrayList<String>();

	public List<String> getKeywordIdList() {
		return keywordIdList;
	}

	public void setKeywordIdList(List<String> keywordIdList) {
		this.keywordIdList = keywordIdList;
	}

	public String getSourceTbname() {
		return sourceTbname;
	}

	public void setSourceTbname(String sourceTbname) {
		this.sourceTbname = sourceTbname;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSubjectNameSearch() {
		return subjectNameSearch;
	}

	public String getSubjectName4Like() {
		if (StringUtils.isNotBlank(subjectNameSearch)) {
			return "%" + subjectNameSearch + "%";
		}
		return "";
	}

	public void setSubjectNameSearch(String subjectNameSearch) {
		this.subjectNameSearch = subjectNameSearch;
	}

	public String getKeywordSearch() {
		return keywordSearch;
	}

	public void setKeywordSearch(String keywordSearch) {
		this.keywordSearch = keywordSearch;
	}

	public String getKeyword4Like() {
		if (StringUtils.isNotBlank(keywordSearch)) {
			return "%" + keywordSearch + "%";
		}
		return "";
	}
}
