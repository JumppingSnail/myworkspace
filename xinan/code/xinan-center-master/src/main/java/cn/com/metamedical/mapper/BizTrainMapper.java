package cn.com.metamedical.mapper;

import cn.com.metamedical.model.entity.BizTrain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Allen
 * @since 2021-10-14
 */
public interface BizTrainMapper extends BaseMapper<BizTrain> {

    IPage<Map<String, Object>> getBizTrainList(IPage<Map<String, Object>> page, @Param("subject") String subject, @Param("state") String state);
    
    IPage<Map<String, Object>> myTrainList(IPage<Map<String, Object>> page, String userId, String orgId);
}
