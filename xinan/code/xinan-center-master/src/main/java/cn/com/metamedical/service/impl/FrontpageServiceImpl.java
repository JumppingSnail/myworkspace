package cn.com.metamedical.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.front.FrontSearchBean;
import cn.com.metamedical.bean.repos.SectionInfoVO;
import cn.com.metamedical.mapper.BaseKnowledgeCatagoryMapper;
import cn.com.metamedical.mapper.BizDiseaseCategoryMapper;
import cn.com.metamedical.mapper.BizFeedbackMapper;
import cn.com.metamedical.mapper.BizKeywordMapper;
import cn.com.metamedical.mapper.BizKnowledgeCollectMapper;
import cn.com.metamedical.mapper.BizKnowledgeContentMapper;
import cn.com.metamedical.mapper.BizKnowledgeMapper;
import cn.com.metamedical.mapper.BizKnowledgeMarkMapper;
import cn.com.metamedical.mapper.BizTrainMapper;
import cn.com.metamedical.model.entity.BaseKnowledgeCatagory;
import cn.com.metamedical.model.entity.BizDiseaseCategory;
import cn.com.metamedical.model.entity.BizFeedback;
import cn.com.metamedical.model.entity.BizKeyword;
import cn.com.metamedical.model.entity.BizKnowledge;
import cn.com.metamedical.model.entity.BizKnowledgeCollect;
import cn.com.metamedical.model.entity.BizKnowledgeContent;
import cn.com.metamedical.model.entity.BizKnowledgeMark;
import cn.com.metamedical.model.entity.BizTrain;
import cn.com.metamedical.service.IFrontpageService;
import cn.com.metamedical.util.tool.CommonTool;

@Service("frontpageService")
public class FrontpageServiceImpl implements IFrontpageService {
	@Autowired
	private CommonTool commonTool;

	@Autowired
	private BizKnowledgeMapper bizKnowledgeMapper;

	@Autowired
	private BizTrainMapper bizTrainMapper;

	@Autowired
	private BizFeedbackMapper bizFeedbackMapper;

	@Autowired
	private BizKnowledgeCollectMapper bizKnowledgeCollectMapper;

	@Autowired
	private BizKnowledgeMarkMapper bizKnowledgeMarkMapper;

	@Autowired
	private BizKnowledgeContentMapper bizKnowledgeContentMapper;

	@Autowired
	private BaseKnowledgeCatagoryMapper baseKnowledgeCatagoryMapper;

	@Autowired
	private BizKeywordMapper bizKeywordMapper;

	@Autowired
	private BizDiseaseCategoryMapper bizDiseaseCategoryMapper;

	@Override
	public List<Map<String, Object>> commendKnowledgeList(FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.portalKnowledgeList(page, "1",
				bean.getCatagoryIdSearch(), "update_time");

		return resPage.getRecords();
	}

	@Override
	public List<Map<String, Object>> newKnowledgeList(FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.portalKnowledgeList(page, "",
				bean.getCatagoryIdSearch(), "create_time");

		return resPage.getRecords();
	}

	@Override
	public IPage<Map<String, Object>> fullSearchList(FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.fullSearchList(page, bean.getCatagoryIdSearch(),
				bean.getFullContent4Like());

		return resPage;
	}

	@Override
	public IPage<Map<String, Object>> subjectDetailList(FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		StringBuilder keywordIds = new StringBuilder();
		if (bean.getKeywordIdList().size() > 0) {
			for (String keywordId : bean.getKeywordIdList()) {
				keywordIds.append("'").append(keywordId).append("',");
			}
			keywordIds.append("'0'");
		}

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.knowledgeWithKeywordList(page, keywordIds.toString(),
				bean.getCatagoryIdSearch(), bean.getSubjectId());

		return resPage;
	}

	@Override
	public List<Map<String, Object>> allKeywordList(FrontSearchBean bean) {
		return bizKeywordMapper.allKeywordList(bean.getSubjectId());
	}

	@Override
	public List<Map<String, Object>> allSubjectList() {
		return bizDiseaseCategoryMapper.selectMaps(new QueryWrapper<BizDiseaseCategory>().orderByAsc("subject_sort"));
	}

	@Override
	public List<Map<String, Object>> myCollectList(UserBean currentUser, FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.myCollectList(page, currentUser.getUserId());

		return resPage.getRecords();
	}

	@Override
	public List<Map<String, Object>> myFeedbackList(UserBean currentUser, FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizKnowledgeMapper.myFeedbackList(page, currentUser.getUserId());

		return resPage.getRecords();
	}

	@Override
	public List<Map<String, Object>> myTrainList(UserBean currentUser, FrontSearchBean bean) {
		IPage<Map<String, Object>> page = new Page<>(bean.getPage(), bean.getLimit());

		IPage<Map<String, Object>> resPage = bizTrainMapper.myTrainList(page, currentUser.getUserId(),
				currentUser.getOrgId());

		return resPage.getRecords();
	}

	@Override
	public Map<String, Object> myTrainDetail(String id) {
		Map<String, Object> row = new HashMap<>();
		BizTrain bean = bizTrainMapper.selectById(id);
		if (bean != null) {
			row.put("train_id", bean.getId());
			row.put("train_subject", bean.getTrainSubject());
			row.put("train_place", bean.getTrainPlace());
			row.put("start_time", bean.getStartTime());
			row.put("end_time", bean.getEndTime());
			row.put("train_state", bean.getTrainState());
			row.put("train_content", bean.getTrainContent());
		}
		return row;
	}

	@Override
	public String saveMyFeedback(UserBean currentUser, BizFeedback entity) {
		entity.setId(commonTool.genTableId());
		entity.setDealState("1");
		entity.setFeedbackTime(LocalDateTime.now());
		entity.setFeedbackUserId(currentUser.getUserId());
		entity.setFeedbackUserName(currentUser.getRealName());

		bizFeedbackMapper.insert(entity);

		return "ok";
	}

	// 查询是否已经收藏（有记录），没有则添加，有则删除
	@Override
	public String saveOrDeleteMyCollect(UserBean currentUser, BizKnowledgeCollect entity) {
		if (bizKnowledgeCollectMapper.selectCount(new QueryWrapper<BizKnowledgeCollect>()
				.eq("repos_id", entity.getReposId()).eq("collect_user_id", currentUser.getUserId())) > 0) {

			bizKnowledgeCollectMapper.delete(new QueryWrapper<BizKnowledgeCollect>().eq("repos_id", entity.getReposId())
					.eq("collect_user_id", currentUser.getUserId()));

		} else {
			entity.setId(commonTool.genTableId());
			entity.setCollectTime(LocalDateTime.now());
			entity.setCollectUserId(currentUser.getUserId());
			entity.setCollectUserName(currentUser.getRealName());

			bizKnowledgeCollectMapper.insert(entity);
		}

		return "ok";
	}

	// 查询如果该处已经有书签则不操作，没有书签则添加
	@Override
	public String saveMyMark(UserBean currentUser, BizKnowledgeMark entity) {

		QueryWrapper<BizKnowledgeMark> query = new QueryWrapper<BizKnowledgeMark>();
		query.eq("create_user_id", currentUser.getUserId());
		if (StringUtils.isNotBlank(entity.getReposId())) {
			query.eq("repos_id", entity.getReposId());
		}
		if (StringUtils.isNotBlank(entity.getSectionId())) {
			query.eq("section_id", entity.getSectionId());
		}

		if (bizKnowledgeMarkMapper.selectCount(query) == 0) {
			entity.setId(commonTool.genTableId());
			entity.setCreateTime(LocalDateTime.now());
			entity.setCreateUserId(currentUser.getUserId());
			entity.setCreateUserName(currentUser.getRealName());

			if (StringUtils.isBlank(entity.getMarkContent())) {
				if (StringUtils.isNotBlank(entity.getSectionId())) {
					String sectionName = bizKnowledgeContentMapper.selectById(entity.getSectionId()).getSectionName();
					entity.setMarkContent(sectionName);
				} else if (StringUtils.isNotBlank(entity.getReposId())) {
					String reposTitle = bizKnowledgeMapper.selectById(entity.getReposId()).getReposTitle();
					entity.setMarkContent(reposTitle);
				}
			}

			bizKnowledgeMarkMapper.insert(entity);
		}

		return "ok";
	}

	@Override
	public List<Map<String, Object>> getMarkList(UserBean currentUser, FrontSearchBean bean) {
		return bizKnowledgeMarkMapper.selectMaps(
				new QueryWrapper<BizKnowledgeMark>().eq("repos_id", bean.getReposId()).orderByAsc("create_time"));
	}

	@Override
	public List<SectionInfoVO> getSectionList(FrontSearchBean bean) {
		List<SectionInfoVO> sectList = new ArrayList<>();

		Map<String, SectionInfoVO> tempMap = new HashMap<>();
		List<BizKnowledgeContent> resList = bizKnowledgeContentMapper.selectList(new QueryWrapper<BizKnowledgeContent>()
				.eq("repos_id", bean.getReposId()).orderByAsc("section_level", "section_sort"));

		resList.stream().forEach(sectBean -> {
			SectionInfoVO inner = new SectionInfoVO();
			inner.setSectionId(sectBean.getId());
			inner.setSectionName(sectBean.getSectionName());

			// 第一级直接添加
			if (sectBean.getSectionLevel() == 1) {
				sectList.add(inner);
				tempMap.put(sectBean.getId(), inner);

			} else if (sectBean.getSectionLevel() == 2) { // 第二级挂载在第一级下
				SectionInfoVO parentBean = tempMap.get(sectBean.getpSectionId());
				if (parentBean != null) {
					parentBean.getChildSectionList().add(inner);
				}
			}
		});

		return sectList;
	}

	// 获取知识库内容首页，如书籍的主体信息
	@Override
	public Map<String, Object> fetchKnowledgeMainInfo(UserBean currentUser, FrontSearchBean bean) {
		Map<String, Object> row = new HashMap<>();

		BizKnowledge mainBean = bizKnowledgeMapper.selectById(bean.getReposId());
		if (mainBean != null) {
			row.put("repos_id", mainBean.getId());
			row.put("repos_title", mainBean.getReposTitle());
			row.put("repos_author", mainBean.getReposAuthor());
			row.put("repos_digest", mainBean.getReposDigest());
			row.put("repos_drug", mainBean.getReposDrug());

			BaseKnowledgeCatagory catBean = baseKnowledgeCatagoryMapper.selectById(mainBean.getCatId());
			row.put("cat_id", mainBean.getCatId());
			row.put("cat_name", catBean.getCatName());
		}

		if (currentUser != null) {
			int count = bizKnowledgeCollectMapper.selectCount(new QueryWrapper<BizKnowledgeCollect>()
					.eq("repos_id", bean.getReposId()).eq("collect_user_id", currentUser.getUserId()));
			if (count > 0) {
				row.put("collect_flag", "1");
			} else {
				row.put("collect_flag", "0");
			}
		} else {
			row.put("collect_flag", "0");
		}

		return row;
	}

	// 如果是书籍，则只会返回指定的章节，如果是其他类型，则会返回全部内容，前端显示不分页
	// 此处判断，只有书籍才需要传章节id，以此判断需要分章节显示
	@Override
	public List<Map<String, Object>> fetchKnowledgeContent(FrontSearchBean bean) {
		List<Map<String, Object>> resList = new ArrayList<>();

		if (StringUtils.isNotBlank(bean.getSectionId())) {
			BizKnowledgeContent contBean = bizKnowledgeContentMapper.selectById(bean.getSectionId());

			Map<String, Object> row = new HashMap<>();
			row.put("section_id", contBean.getId());
			row.put("section_name", contBean.getSectionName());
			row.put("section_content", contBean.getSectionContent());
			resList.add(row);

		} else {
			bizKnowledgeContentMapper.selectList(new QueryWrapper<BizKnowledgeContent>()
					.eq("repos_id", bean.getReposId()).orderByAsc("section_sort")).stream().forEach(contBean -> {
						Map<String, Object> row = new HashMap<>();
						row.put("section_id", contBean.getId());
						row.put("section_name", contBean.getSectionName());
						row.put("section_content", contBean.getSectionContent());
						resList.add(row);
					});
		}

		return resList;
	}

}
