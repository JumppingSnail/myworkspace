package cn.com.metamedical.service.impl;

import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.mapper.BaseOrgMapper;
import cn.com.metamedical.model.entity.BaseOrg;
import cn.com.metamedical.service.IBaseOrgService;
import cn.com.metamedical.util.tool.CommonTool;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@Service
public class BaseOrgServiceImpl implements IBaseOrgService {

    @Resource
    private BaseOrgMapper baseOrgMapper;

    @Resource
    private CommonTool commonTool;

    @Override
    public IPage<Map<String, Object>> getOrgList(SysSearchBean searchBean) {
        IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
        LambdaQueryWrapper<BaseOrg> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(BaseOrg::getCreateDate);
        return baseOrgMapper.selectMapsPage(page, lambdaQueryWrapper);
    }

    @Override
    public List<BaseOrg> getNoPageList(SysSearchBean bean) {
        LambdaQueryWrapper<BaseOrg> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(BaseOrg::getCreateDate);
        return baseOrgMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public String saveOrg(BaseOrg entity) {
        if (StringUtils.isNotBlank(entity.getId())){
            BaseOrg baseOrg = baseOrgMapper.selectById(entity.getId());
            BeanUtils.copyProperties(entity, baseOrg);
            baseOrgMapper.updateById(baseOrg);
        } else {
            entity.setId(commonTool.genTableId());
            entity.setCreateDate(LocalDate.now());
            baseOrgMapper.insert(entity);
        }

        return "ok";
    }
}
