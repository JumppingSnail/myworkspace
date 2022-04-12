package cn.com.metamedical.util.comm;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartHandler implements ApplicationRunner {
	private static Logger log = LoggerFactory.getLogger(ApplicationStartHandler.class);

	@Value("${yzmeta.upload-dir}")
	private String uploadDir;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("==========Application Start Setting============");

		// 创建文件夹
		checkAndCreateDir();

	}

	private void checkAndCreateDir() {

		File file = new File(uploadDir);
		if (!file.exists()) {
			file.mkdir();
		}
	}
}
