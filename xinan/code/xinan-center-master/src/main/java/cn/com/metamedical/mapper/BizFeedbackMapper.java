package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BizFeedback;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-10-15
 */
public interface BizFeedbackMapper extends BaseMapper<BizFeedback> {
	IPage<Map<String, Object>> feedbackList(IPage<Map<String, Object>> page, String content, String state);
}
