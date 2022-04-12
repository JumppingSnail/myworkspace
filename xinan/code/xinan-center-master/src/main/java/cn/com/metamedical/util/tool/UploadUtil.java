/**
 * 
 */
package cn.com.metamedical.util.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhouyunbo
 *
 */
@Component
public class UploadUtil {
	private static Logger log = LoggerFactory.getLogger(UploadUtil.class);
	
	@Value("${yzmeta.upload-dir}")
	private String uploadDirRoot;
	
	@Value("${yzmeta.allow-file-type:all}")
	private String allowFileType;
	
	/**
	 * 上传文件到系统上传目录
	 * */
	public String saveFileToDisk(InputStream fin, String fileName){
		String monthDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

		File file = new File(uploadDirRoot + "/" + monthDir);
		if (!file.exists()) {
			file.mkdirs();
		}

		try (FileOutputStream fout = new FileOutputStream(uploadDirRoot + "/" + monthDir + "/" + fileName)) {
			byte[] buf = new byte[1024];
			int len;
			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}
			fout.flush();
			fin.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			return "";
		}

		return "/upload_files/" + monthDir + "/" + fileName;
	}
	
	public boolean isRightFile(String fileName) {
		if("all".equalsIgnoreCase(allowFileType)|| StringUtils.isBlank(allowFileType) || StringUtils.isBlank(fileName)) {
			return true;
		}
		
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		if(StringUtils.isBlank(suffix)) {
			return true;
		}
		
		String[] arr = allowFileType.split("[|]");
		for (String str : arr) {
			if(suffix.trim().equalsIgnoreCase(str.trim())) {
				return true;
			}
		}
		
		return false;
	}
}
