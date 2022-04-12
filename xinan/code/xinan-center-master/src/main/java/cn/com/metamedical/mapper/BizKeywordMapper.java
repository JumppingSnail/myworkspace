package cn.com.metamedical.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.model.entity.BizKeyword;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2022-02-26
 */
public interface BizKeywordMapper extends BaseMapper<BizKeyword> {

	IPage<Map<String, Object>> linkedKeywordList(IPage<Map<String, Object>> page, String sourceId);

	IPage<Map<String, Object>> unlinkedKeywordList(IPage<Map<String, Object>> page, String sourceId, String keyword);
	
	List<Map<String, Object>> allKeywordList(String subjectId);
	
}
