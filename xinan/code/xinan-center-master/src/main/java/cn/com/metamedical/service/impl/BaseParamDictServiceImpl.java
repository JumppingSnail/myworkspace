package cn.com.metamedical.service.impl;

import cn.com.metamedical.bean.comm.DictSearchBean;
import cn.com.metamedical.mapper.BaseParamDictMapper;
import cn.com.metamedical.model.entity.BaseParamDict;
import cn.com.metamedical.service.IBaseParamDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@Service
public class BaseParamDictServiceImpl implements IBaseParamDictService {

    @Resource
    private BaseParamDictMapper baseParamDictMapper;

    @Override
    public List<BaseParamDict> queryDictList(DictSearchBean searchBean) {
        LambdaQueryWrapper<BaseParamDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseParamDict::getParamCategory, searchBean.getParamCategory())
                .orderByAsc(BaseParamDict::getParamSort);
        List<BaseParamDict> baseParamDicts = baseParamDictMapper.selectList(lambdaQueryWrapper);
        return baseParamDicts;
    }
}
