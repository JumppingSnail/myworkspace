package cn.com.metamedical.mapper;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.model.entity.BizKnowledge;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-10-15
 */
public interface BizKnowledgeMapper extends BaseMapper<BizKnowledge> {
	IPage<Map<String, Object>> reposList(IPage<Map<String, Object>> page, String reposTitle, String catId,
			String reposState, String createUserId, String catName);

	IPage<Map<String, Object>> fullSearchList(IPage<Map<String, Object>> page, String catId, String searchContent);

	IPage<Map<String, Object>> myCollectList(IPage<Map<String, Object>> page, String userId);

	IPage<Map<String, Object>> myFeedbackList(IPage<Map<String, Object>> page, String userId);
	
	
	IPage<Map<String, Object>> portalKnowledgeList(IPage<Map<String, Object>> page, String commendFlag, String catId, String orderBy);
	
	IPage<Map<String, Object>> knowledgeWithKeywordList(IPage<Map<String, Object>> page, String keywordIds, String catId, String subjectId);

}
