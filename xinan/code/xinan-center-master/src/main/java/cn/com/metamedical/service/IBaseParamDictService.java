package cn.com.metamedical.service;

import cn.com.metamedical.bean.comm.DictSearchBean;
import cn.com.metamedical.model.entity.BaseParamDict;

import java.util.List;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
public interface IBaseParamDictService {

    List<BaseParamDict> queryDictList(DictSearchBean searchBean);

}
