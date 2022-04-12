package cn.com.metamedical.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.study.StudySearchBean;
import cn.com.metamedical.mapper.BizDiseaseCategoryMapper;
import cn.com.metamedical.mapper.BizKeywordMapper;
import cn.com.metamedical.mapper.BizKeywordRelMapper;
import cn.com.metamedical.model.entity.BizDiseaseCategory;
import cn.com.metamedical.model.entity.BizKeyword;
import cn.com.metamedical.model.entity.BizKeywordRel;
import cn.com.metamedical.service.IStudyManageService;
import cn.com.metamedical.util.tool.CommonTool;

@Service("studyManageService")
public class StudyManageServiceImpl implements IStudyManageService {
	@Autowired
	private CommonTool commonTool;

	@Autowired
	private BizDiseaseCategoryMapper bizDiseaseCategoryMapper;

	@Autowired
	private BizKeywordMapper bizKeywordMapper;

	@Autowired
	private BizKeywordRelMapper bizKeywordRelMapper;

	@Override
	public IPage<Map<String, Object>> subjectList(StudySearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());

		QueryWrapper<BizDiseaseCategory> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(searchBean.getSubjectNameSearch())) {
			queryWrapper.like("subject_name", searchBean.getSubjectNameSearch());
		}
		queryWrapper.orderByAsc("subject_sort");

		return bizDiseaseCategoryMapper.selectMapsPage(page, queryWrapper);
	}

	@Override
	public IPage<Map<String, Object>> keywordList(StudySearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());

		QueryWrapper<BizKeyword> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(searchBean.getKeywordSearch())) {
			queryWrapper.like("keyword_name", searchBean.getKeywordSearch());
		}
		queryWrapper.orderByAsc("keyword_name");

		return bizKeywordMapper.selectMapsPage(page, queryWrapper);
	}

	@Override
	public IPage<Map<String, Object>> linkedKeywordList(StudySearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
		return bizKeywordMapper.linkedKeywordList(page, searchBean.getSourceId());
	}

	@Override
	public IPage<Map<String, Object>> unlinkedKeywordList(StudySearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
		
		return bizKeywordMapper.unlinkedKeywordList(page, searchBean.getSourceId(), searchBean.getKeyword4Like());
	}

	@Transactional
	@Override
	public String saveKeywordRel(StudySearchBean beanEntity) {
		for (String keywordId : beanEntity.getKeywordIdList()) {
			BizKeywordRel relBean = new BizKeywordRel();
			relBean.setId(commonTool.genTableId());
			relBean.setKeywordId(keywordId);
			relBean.setSourceId(beanEntity.getSourceId());
			relBean.setSourceTbname(beanEntity.getSourceTbname());
			bizKeywordRelMapper.insert(relBean);
		}
		
		return "ok";
	}

	@Override
	public String deleteLinkedKeyword(String id) {
		bizKeywordRelMapper.deleteById(id);
		return "ok";
	}

	@Override
	public String deleteSubject(String id) {
		bizDiseaseCategoryMapper.deleteById(id);
		return "ok";
	}

	@Transactional
	@Override
	public String deleteKeyword(String id) {
		bizKeywordMapper.deleteById(id);
		bizKeywordRelMapper.delete(new QueryWrapper<BizKeywordRel>().eq("keyword_id", id));
		return "ok";
	}

	@Override
	public String saveSubject(BizDiseaseCategory entity) {
		if (StringUtils.isBlank(entity.getId())) {
			if (bizDiseaseCategoryMapper.selectCount(
					new QueryWrapper<BizDiseaseCategory>().eq("subject_name", entity.getSubjectName())) > 0) {
				return "310";
			}

			entity.setId(commonTool.genTableId());
			bizDiseaseCategoryMapper.insert(entity);

		} else {
			if (bizDiseaseCategoryMapper.selectCount(new QueryWrapper<BizDiseaseCategory>()
					.eq("subject_name", entity.getSubjectName()).ne("id", entity.getId())) > 0) {
				return "310";
			}

			bizDiseaseCategoryMapper.updateById(entity);
		}

		return "ok";
	}

	@Override
	public String saveKeyword(BizKeyword entity) {
		if (StringUtils.isBlank(entity.getId())) {
			if (bizKeywordMapper
					.selectCount(new QueryWrapper<BizKeyword>().eq("keyword_name", entity.getKeywordName())) > 0) {
				return "310";
			}

			entity.setId(commonTool.genTableId());
			bizKeywordMapper.insert(entity);

		} else {
			if (bizKeywordMapper.selectCount(new QueryWrapper<BizKeyword>().eq("keyword_name", entity.getKeywordName())
					.ne("id", entity.getId())) > 0) {
				return "310";
			}

			bizKeywordMapper.updateById(entity);
		}

		return "ok";
	}

}
