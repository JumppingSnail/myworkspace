package cn.com.metamedical.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.repos.ReposSearchBean;
import cn.com.metamedical.model.entity.BaseKnowledgeCatagory;

public interface ICatagoryService {
	public IPage<Map<String, Object>> catagorylist(ReposSearchBean bean);

	public List<Map<String, Object>> allCatagorylist();

	public String changeCatagoryState(String id, String newState);

	public String saveCatagory(BaseKnowledgeCatagory entity);
	
}
