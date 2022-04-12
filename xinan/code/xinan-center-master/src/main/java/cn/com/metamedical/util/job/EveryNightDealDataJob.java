package cn.com.metamedical.util.job;

import cn.com.metamedical.service.IBizTrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class EveryNightDealDataJob {
	private static Logger log = LoggerFactory.getLogger(EveryNightDealDataJob.class);

	@Resource
	private IBizTrainService bizTrainService;

	@Scheduled(cron = "0 0 1 * * ?")
	public void cronJobFun() {
		log.info("============EveryNightDealDataJob START=================");
		bizTrainService.updateEndTrainTask();
		log.info("-------------EveryNightDealDataJob END-------------------");
	}
}
