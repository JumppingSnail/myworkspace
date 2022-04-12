package cn.com.metamedical.service;

import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.model.entity.BaseOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public interface IBaseOrgService {

    IPage<Map<String, Object>> getOrgList(SysSearchBean bean);

    String saveOrg(BaseOrg entity);

    List<BaseOrg> getNoPageList(SysSearchBean bean);
}
