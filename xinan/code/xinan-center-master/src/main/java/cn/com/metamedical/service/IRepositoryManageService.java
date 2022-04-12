package cn.com.metamedical.service;

import java.util.List;
import java.util.Map;

import cn.com.metamedical.bean.repos.SectionListSelVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.repos.ReposInfoVO;
import cn.com.metamedical.bean.repos.ReposSearchBean;
import cn.com.metamedical.bean.repos.SectionListVO;
import cn.com.metamedical.model.entity.BizKnowledge;
import cn.com.metamedical.model.entity.BizKnowledgeContent;

public interface IRepositoryManageService {
	public IPage<Map<String, Object>> reposList(ReposSearchBean bean);

	public IPage<Map<String, Object>> reposLog(ReposSearchBean bean);

	public ReposInfoVO fetchRepositoryInfo(String id);

	public BizKnowledgeContent fetchSectionContent(String id);

	public String changeReposState(String id, String newState, UserBean currentUser);

	public String changeCommendState(String id, String newState, UserBean currentUser);

	public String saveRepos(BizKnowledge entity, UserBean currentUser);

	public String saveSection(BizKnowledgeContent entity, UserBean currentUser);

	public String deleteSection(String sectionId);

	public List<SectionListVO> getSectionList(String reposId);

	public String saveSectionSort(List<SectionListVO> list);

	List<SectionListSelVO> getSectionListSel(String reposId);
}
