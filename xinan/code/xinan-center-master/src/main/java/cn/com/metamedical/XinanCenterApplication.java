package cn.com.metamedical;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@ServletComponentScan
@MapperScan("cn.com.metamedical.mapper")
public class XinanCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(XinanCenterApplication.class, args);
	}

}
