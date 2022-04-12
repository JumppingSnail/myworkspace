package cn.com.metamedical.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.study.StudySearchBean;
import cn.com.metamedical.model.entity.BizDiseaseCategory;
import cn.com.metamedical.model.entity.BizKeyword;

public interface IStudyManageService {
	public IPage<Map<String, Object>> subjectList(StudySearchBean bean);

	public IPage<Map<String, Object>> keywordList(StudySearchBean bean);

	public IPage<Map<String, Object>> linkedKeywordList(StudySearchBean bean);

	public IPage<Map<String, Object>> unlinkedKeywordList(StudySearchBean bean);

	public String deleteSubject(String id);

	public String deleteKeyword(String id);

	public String deleteLinkedKeyword(String id);

	public String saveSubject(BizDiseaseCategory entity);

	public String saveKeyword(BizKeyword entity);

	public String saveKeywordRel(StudySearchBean beanEntity);
}
