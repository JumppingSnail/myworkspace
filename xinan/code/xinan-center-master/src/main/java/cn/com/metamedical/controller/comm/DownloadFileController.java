package cn.com.metamedical.controller.comm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadFileController {
	private static Logger log = LoggerFactory.getLogger(DownloadFileController.class);

	@Value("${yzmeta.upload-dir}")
	private String uploadDir;

	@RequestMapping("/upload_files/**")
	public void getHtml(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/octet-stream");

		String uri = request.getRequestURI();
		String[] arr = uri.split("upload_files/");
		String resourceName = "";
		if (arr.length > 1) {
			resourceName = arr[1];
		}

		String url = uploadDir + "/" + resourceName;

		BufferedInputStream bis = null;
		try {
			OutputStream outputStream = response.getOutputStream();
//			response.setContentType("application/octet-stream;charset=utf-8");
//			response.setHeader("Content-Disposition", "attachment;filename="+path.getFileName());

			FileInputStream fin = new FileInputStream(new File(url));
			response.setHeader("Content-Length", String.valueOf(fin.getChannel().size()));

			byte[] buff = new byte[1024];
			// 读取filename
			bis = new BufferedInputStream(fin);
			int i = bis.read(buff);
			while (i != -1) {
				outputStream.write(buff, 0, buff.length);
				outputStream.flush();
				i = bis.read(buff);
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (null != bis) {
					bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
