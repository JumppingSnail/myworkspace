package cn.com.metamedical.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.repos.ReposSearchBean;
import cn.com.metamedical.mapper.BaseKnowledgeCatagoryMapper;
import cn.com.metamedical.model.entity.BaseKnowledgeCatagory;
import cn.com.metamedical.service.ICatagoryService;
import cn.com.metamedical.util.tool.CommonTool;

@Service("catagoryService")
public class CatagoryServiceImpl implements ICatagoryService {
	@Autowired
	private CommonTool commonTool;

	@Autowired
	private BaseKnowledgeCatagoryMapper baseKnowledgeCatagoryMapper;

	@Override
	public IPage<Map<String, Object>> catagorylist(ReposSearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
		QueryWrapper<BaseKnowledgeCatagory> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(searchBean.getCatNameSearch())) {
			queryWrapper.like("cat_name", searchBean.getCatName4Like());
		}
		queryWrapper.orderByAsc("cat_sort");

		return baseKnowledgeCatagoryMapper.selectMapsPage(page, queryWrapper);
	}

	@Override
	public List<Map<String, Object>> allCatagorylist() {
		return baseKnowledgeCatagoryMapper.selectMaps(new QueryWrapper<BaseKnowledgeCatagory>().eq("cat_state", "1").orderByAsc("cat_sort"));
	}

	@Override
	public String changeCatagoryState(String id, String newState) {
		if (StringUtils.isNotBlank(id)) {
			BaseKnowledgeCatagory entity = new BaseKnowledgeCatagory();
			entity.setId(id);
			entity.setCatState(newState);
			baseKnowledgeCatagoryMapper.updateById(entity);
		}

		return "ok";
	}

	@Override
	public String saveCatagory(BaseKnowledgeCatagory entity) {

		if (StringUtils.isBlank(entity.getId())) {
			if (baseKnowledgeCatagoryMapper
					.selectCount(new QueryWrapper<BaseKnowledgeCatagory>().eq("cat_name", entity.getCatName())) > 0) {
				return "310";
			}

			entity.setId(commonTool.genTableId());
			entity.setCreateDate(LocalDate.now());
			entity.setCatState("1");
			baseKnowledgeCatagoryMapper.insert(entity);

		} else {
			if (baseKnowledgeCatagoryMapper.selectCount(new QueryWrapper<BaseKnowledgeCatagory>()
					.eq("cat_name", entity.getCatName()).ne("id", entity.getId())) > 0) {
				return "310";
			}

			baseKnowledgeCatagoryMapper.updateById(entity);
		}

		return "ok";
	}

}
