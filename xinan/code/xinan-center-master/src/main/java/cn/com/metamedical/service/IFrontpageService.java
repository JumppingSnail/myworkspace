package cn.com.metamedical.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.front.FrontSearchBean;
import cn.com.metamedical.bean.repos.SectionInfoVO;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.model.entity.BizKnowledgeCollect;
import cn.com.metamedical.model.entity.BizKnowledgeMark;

public interface IFrontpageService {
	public List<Map<String, Object>> commendKnowledgeList(FrontSearchBean bean);

	public List<Map<String, Object>> newKnowledgeList(FrontSearchBean bean);

	public IPage<Map<String, Object>> fullSearchList(FrontSearchBean bean);

	public List<Map<String, Object>> myCollectList(UserBean currentUser, FrontSearchBean bean);

	public List<Map<String, Object>> myFeedbackList(UserBean currentUser, FrontSearchBean bean);

	public List<Map<String, Object>> myTrainList(UserBean currentUser, FrontSearchBean bean);

	public Map<String, Object> myTrainDetail(String id);

	public String saveMyFeedback(UserBean currentUser, BizFeedback entity);

	public String saveOrDeleteMyCollect(UserBean currentUser, BizKnowledgeCollect entity);

	public String saveMyMark(UserBean currentUser, BizKnowledgeMark entity);

	public Map<String, Object> fetchKnowledgeMainInfo(UserBean currentUser, FrontSearchBean bean);

	public List<Map<String, Object>> fetchKnowledgeContent(FrontSearchBean bean);

	public List<Map<String, Object>> getMarkList(UserBean currentUser, FrontSearchBean bean);

	public List<SectionInfoVO> getSectionList(FrontSearchBean bean);

	public List<Map<String, Object>> allKeywordList(FrontSearchBean bean);

	public List<Map<String, Object>> allSubjectList();
	
	public IPage<Map<String, Object>> subjectDetailList(FrontSearchBean bean);
	
}
