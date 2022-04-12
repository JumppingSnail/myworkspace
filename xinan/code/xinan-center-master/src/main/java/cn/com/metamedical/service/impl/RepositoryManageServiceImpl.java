package cn.com.metamedical.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.metamedical.bean.repos.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.mapper.BizFeedbackMapper;
import cn.com.metamedical.mapper.BizKnowledgeContentMapper;
import cn.com.metamedical.mapper.BizKnowledgeLogMapper;
import cn.com.metamedical.mapper.BizKnowledgeMapper;
import cn.com.metamedical.mapper.BizKnowledgeMarkMapper;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.model.entity.BizKnowledge;
import cn.com.metamedical.model.entity.BizKnowledgeContent;
import cn.com.metamedical.model.entity.BizKnowledgeLog;
import cn.com.metamedical.model.entity.BizKnowledgeMark;
import cn.com.metamedical.service.IRepositoryManageService;
import cn.com.metamedical.util.tool.CommonTool;

@Service("repositoryService")
public class RepositoryManageServiceImpl implements IRepositoryManageService {
	@Autowired
	private CommonTool commonTool;

	@Autowired
	private BizKnowledgeMapper bizKnowledgeMapper;

	@Autowired
	private BizKnowledgeLogMapper bizKnowledgeLogMapper;

	@Autowired
	private BizKnowledgeContentMapper bizKnowledgeContentMapper;
	
	@Autowired
	private BizKnowledgeMarkMapper bizKnowledgeMarkMapper;
	
	@Autowired
	private BizFeedbackMapper bizFeedbackMapper;

	@Override
	public IPage<Map<String, Object>> reposList(ReposSearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());

		return bizKnowledgeMapper.reposList(page, searchBean.getReposTitle4Like(), searchBean.getCatId(),
				searchBean.getReposStateSearch(), searchBean.getCreateUserId(), searchBean.getCatNameSearch());
	}

	@Override
	public IPage<Map<String, Object>> reposLog(ReposSearchBean searchBean) {
		IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());

		return bizKnowledgeLogMapper.selectMapsPage(page,
				new QueryWrapper<BizKnowledgeLog>().eq("repos_id", searchBean.getReposId()).orderByAsc("create_time"));
	}

	@Override
	public String changeReposState(String id, String newState, UserBean currentUser) {
		if (StringUtils.isNotBlank(id)) {
			BizKnowledge entity = new BizKnowledge();
			entity.setId(id);
			entity.setReposState(newState);
			bizKnowledgeMapper.updateById(entity);

			BizKnowledgeLog logBean = new BizKnowledgeLog();
			logBean.setId(commonTool.genTableId());
			logBean.setReposId(id);
			logBean.setCreateTime(LocalDateTime.now());
			logBean.setCreateUserId(currentUser.getUserId());
			logBean.setCreateUserName(currentUser.getRealName());

			// 修改为已上架
			if ("1".equals(newState)) {
				logBean.setChangeContent("将状态修改为【上架】");
			} else {
				logBean.setChangeContent("将状态修改为【下架】");
			}

			bizKnowledgeLogMapper.insert(logBean);
		}

		return "ok";
	}

	@Override
	public String changeCommendState(String id, String newState, UserBean currentUser) {
		if (StringUtils.isNotBlank(id)) {
			BizKnowledge entity = new BizKnowledge();
			entity.setId(id);
			entity.setCommendFlag(newState);
			bizKnowledgeMapper.updateById(entity);

			BizKnowledgeLog logBean = new BizKnowledgeLog();
			logBean.setId(commonTool.genTableId());
			logBean.setReposId(id);
			logBean.setCreateTime(LocalDateTime.now());
			logBean.setCreateUserId(currentUser.getUserId());
			logBean.setCreateUserName(currentUser.getRealName());

			// 修改为已上架
			if ("1".equals(newState)) {
				logBean.setChangeContent("将首页推荐修改为【推荐】");
			} else {
				logBean.setChangeContent("将首页推荐修改为【不推荐】");
			}

			bizKnowledgeLogMapper.insert(logBean);
		}

		return "ok";
	}

	@Override
	public ReposInfoVO fetchRepositoryInfo(String id) {
		ReposInfoVO resBean = new ReposInfoVO();

		BizKnowledge knowledge = bizKnowledgeMapper.selectById(id);
		resBean.setReposBean(knowledge);

		List<SectionInfoVO> sectList = new ArrayList<>();
		resBean.setSectionList(sectList);

		Map<String, SectionInfoVO> tempMap = new HashMap<>();

		List<BizKnowledgeContent> resList = bizKnowledgeContentMapper.selectList(
				new QueryWrapper<BizKnowledgeContent>().eq("repos_id", id).orderByAsc("section_level", "section_sort"));
		resList.stream().forEach(bean -> {
			SectionInfoVO inner = new SectionInfoVO();
			inner.setSectionId(bean.getId());
			inner.setSectionName(bean.getSectionName());

			// 第一级直接添加
			if (bean.getSectionLevel() == 1) {
				sectList.add(inner);
				tempMap.put(bean.getId(), inner);

			} else if (bean.getSectionLevel() == 2) { // 第二级挂载在第一级下
				SectionInfoVO parentBean = tempMap.get(bean.getpSectionId());
				if (parentBean != null) {
					parentBean.getChildSectionList().add(inner);
				}
			}
		});

		return resBean;
	}

	@Override
	public BizKnowledgeContent fetchSectionContent(String id) {
		return bizKnowledgeContentMapper.selectById(id);
	}

	@Override
	public String saveRepos(BizKnowledge entity, UserBean currentUser) {
		String logContent = "";
		entity.setUpdateTime(LocalDateTime.now());
		
		if (StringUtils.isBlank(entity.getId())) {
			if (bizKnowledgeMapper
					.selectCount(new QueryWrapper<BizKnowledge>().eq("repos_title", entity.getReposTitle())) > 0) {
				return "repeat";
			}

			entity.setId(commonTool.genTableId());
			entity.setCreateTime(LocalDateTime.now());
			entity.setCreateUserId(currentUser.getUserId());
			entity.setCreateUserName(currentUser.getRealName());
			entity.setReposUrl(entity.getReposUrl()+entity.getId());
			
			entity.setReposState("0"); // 默认“未上架”

			bizKnowledgeMapper.insert(entity);
			
			logContent = "创建本文档";
			
		}else {
			if (bizKnowledgeMapper
					.selectCount(new QueryWrapper<BizKnowledge>().eq("repos_title", entity.getReposTitle()).ne("id", entity.getId())) > 0) {
				return "repeat";
			}
			
			bizKnowledgeMapper.updateById(entity);
			
			logContent = "修改本文档";
		}
		
		BizKnowledgeLog logBean = new BizKnowledgeLog();
		logBean.setId(commonTool.genTableId());
		logBean.setReposId(entity.getId());
		logBean.setCreateTime(LocalDateTime.now());
		logBean.setCreateUserId(currentUser.getUserId());
		logBean.setCreateUserName(currentUser.getRealName());
		logBean.setChangeContent(logContent);

		bizKnowledgeLogMapper.insert(logBean);

		return "ok";
	}

	@Override
	public String saveSection(BizKnowledgeContent entity, UserBean currentUser) {
		entity.setUpdateTime(LocalDateTime.now());

		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(commonTool.genTableId());
			entity.setCreateTime(LocalDateTime.now());
			entity.setCreateUserId(currentUser.getUserId());
			entity.setCreateUserName(currentUser.getRealName());

			bizKnowledgeContentMapper.insert(entity);

		} else {
			bizKnowledgeContentMapper.updateById(entity);
		}

		if (StringUtils.isNotBlank(entity.getSectionContent())) {
			BizKnowledgeLog logBean = new BizKnowledgeLog();
			logBean.setId(commonTool.genTableId());
			logBean.setReposId(entity.getReposId());
			logBean.setCreateTime(LocalDateTime.now());
			logBean.setCreateUserId(currentUser.getUserId());
			logBean.setCreateUserName(currentUser.getRealName());
			logBean.setChangeContent("编辑或更新文档内容，涉及章节为：" + entity.getSectionName());

			bizKnowledgeLogMapper.insert(logBean);
		}

		return entity.getId();
	}

	@Override
	public List<SectionListVO> getSectionList(String reposId) {
		List<SectionListVO> resList = new ArrayList<>();

		List<BizKnowledgeContent> list = bizKnowledgeContentMapper.selectList(new QueryWrapper<BizKnowledgeContent>()
				.eq("repos_id", reposId).orderByAsc("section_level", "section_sort"));

		Map<String, SectionListVO> beanMap = new HashMap<>();

		list.stream().forEach(bean -> {
			SectionListVO voBean = new SectionListVO(bean);
			if (StringUtils.isNotBlank(bean.getpSectionId()) && beanMap.containsKey(bean.getpSectionId())) {
				beanMap.get(bean.getpSectionId()).getChildren().add(voBean);

			} else {
				if (bean.getSectionLevel() == 1) {
					resList.add(voBean);
				}
			}

			beanMap.put(bean.getId(), voBean);
		});

		return resList;
	}

	@Transactional
	@Override
	public String deleteSection(String sectionId) {
		bizKnowledgeContentMapper.deleteById(sectionId);
		bizKnowledgeMarkMapper.delete(new QueryWrapper<BizKnowledgeMark>().eq("section_id", sectionId));
		bizFeedbackMapper.delete(new QueryWrapper<BizFeedback>().eq("section_id", sectionId));

		return "ok";
	}

	@Override
	public String saveSectionSort(List<SectionListVO> list) {
		list.stream().forEach(entity -> {
			entity.setUpdateTime(LocalDateTime.now());
			bizKnowledgeContentMapper.updateById(entity);
		});
		return "ok";
	}

	@Override
	public List<SectionListSelVO> getSectionListSel(String reposId) {
		List<SectionListSelVO> resList = new ArrayList<>();

		List<BizKnowledgeContent> list = bizKnowledgeContentMapper.selectList(new QueryWrapper<BizKnowledgeContent>()
				.eq("repos_id", reposId).orderByAsc("section_level", "section_sort"));

		Map<String, SectionListSelVO> beanMap = new HashMap<>();

		list.stream().forEach(bean -> {
			SectionListSelVO voBean = new SectionListSelVO(bean);
			if (StringUtils.isNotBlank(bean.getpSectionId()) && beanMap.containsKey(bean.getpSectionId())) {
				beanMap.get(bean.getpSectionId()).getChildren().add(voBean);

			} else {
				if (bean.getSectionLevel() == 1) {
					resList.add(voBean);
				}
			}

			beanMap.put(bean.getId(), voBean);
		});

		return resList;	}
}
