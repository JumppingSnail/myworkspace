package gen;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class CodeGenerator {
	private final String BASE_PACKAGE_NAME = "cn.com.metamedical";
	private final String OUTPUT_JAVA_PATH = "d:/temp/output_code/src/main/java";
	private final String OUTPUT_RESOURCE_PATH = "d:/temp/output_code/src/main/resources";
	
	private final DbType DB_TYPE = DbType.POSTGRE_SQL;
	private final String CLASS_NAME = "org.postgresql.Driver";
	private final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/xinan-db";
	private final String CONNECTION_USER = "xinandb";
	private final String CONNECTION_PASSWD = "xinandb@pg2021#!";
	private final String DATABASE_SCHEMA = "xinandb";

	public static void main(String[] args) {
//		String[] tbNames = {"biz_knowledge","biz_knowledge_log","biz_knowledge_content","biz_knowledge_mark","biz_knowledge_collect","biz_feedback"};
		String[] tbNames = { "biz_disease_category", "biz_keyword_rel", "biz_keyword" };
		new CodeGenerator().generator(tbNames);
	}

	public void generator(String[] tableNames) {

		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		mpg.setGlobalConfig(this.getGlobalConfig());
		mpg.setDataSource(this.getDataSourceConfig());
		mpg.setPackageInfo(this.getPackageConfig());
		mpg.setCfg(this.getInjectionConfig());
		mpg.setTemplate(new TemplateConfig().setXml(null));
		mpg.setStrategy(this.getStrategyConfig(tableNames));
		mpg.setTemplateEngine(new VelocityTemplateEngine());

		mpg.execute();
		if (tableNames == null) {
			System.err.println(" Generator Success !");
		} else {
			System.out.println(" TableName【 " + String.join(" ; ", tableNames) + " 】" + "Generator Success !");

		}
	}

	private GlobalConfig getGlobalConfig() {
		return new GlobalConfig().setOutputDir(OUTPUT_JAVA_PATH)// 输出目录
				.setFileOverride(false)// 是否覆盖文件
				.setActiveRecord(false)// 开启 activeRecord 模式
				.setEnableCache(false)// XML 二级缓存
				.setBaseResultMap(false)// XML ResultMap
				.setBaseColumnList(false)// XML columList
				.setKotlin(false) // 是否生成 kotlin 代码
				.setOpen(false).setAuthor("Allen") // 作者
				// 自定义文件命名，注意 %s 会自动填充表实体属性！
				.setEntityName("%s").setMapperName("%sMapper").setXmlName("%sMapper").setServiceName("I%sService")
				.setServiceImplName("%sServiceImpl").setControllerName("%sController");
	}

	private DataSourceConfig getDataSourceConfig() {
		return new DataSourceConfig().setDbType(DB_TYPE).setDriverName(this.CLASS_NAME)
				.setUsername(this.CONNECTION_USER).setPassword(this.CONNECTION_PASSWD).setUrl(this.CONNECTION_STRING).setSchemaName(this.DATABASE_SCHEMA);
	}

	private StrategyConfig getStrategyConfig(String[] tableNames) {
		return new StrategyConfig().setCapitalMode(false)// 全局大写命名
				.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
				.setInclude(tableNames) // 需要生成的表
				// 【实体】是否生成字段常量（默认 false）
				.setEntityColumnConstant(true)
				// 【实体】是否为构建者模型（默认 false）
				.setEntityBuilderModel(false)
				// 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
				.setEntityLombokModel(false)
				// Boolean类型字段是否移除is前缀处理
				.setEntityBooleanColumnRemoveIsPrefix(true).setRestControllerStyle(true);
	}

	private PackageConfig getPackageConfig() {
		return new PackageConfig().setParent(BASE_PACKAGE_NAME).setController("controller").setEntity("model.entity")
				.setMapper("mapper").setService("service").setServiceImpl("service.impl");
	}

	private InjectionConfig getInjectionConfig() {
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return OUTPUT_RESOURCE_PATH + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		cfg.setFileOutConfigList(focList);

		return cfg;

	}
}
