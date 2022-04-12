package cn.com.metamedical.bean.other;

import org.apache.commons.lang3.StringUtils;

import cn.com.metamedical.bean.comm.BaseBean;

public class FeedbackSearchBean extends BaseBean {
	private String feedbackContentSearch;
	private String dealStateSearch;

	public String getFeedbackContentSearch() {
		return feedbackContentSearch;
	}

	public void setFeedbackContentSearch(String feedbackContentSearch) {
		this.feedbackContentSearch = feedbackContentSearch;
	}

	public String getDealStateSearch() {
		return dealStateSearch;
	}

	public void setDealStateSearch(String dealStateSearch) {
		this.dealStateSearch = dealStateSearch;
	}

	public String getFeedbackContent4Like() {
		if (StringUtils.isNotBlank(feedbackContentSearch)) {
			return "%" + feedbackContentSearch + "%";
		}
		return "";
	}
}
