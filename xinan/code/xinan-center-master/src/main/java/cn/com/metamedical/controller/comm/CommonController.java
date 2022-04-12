package cn.com.metamedical.controller.comm;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.tool.UploadUtil;

@RestController
@RequestMapping("/pub")
public class CommonController {

	@Autowired
	private ControllerTool controllerTool;

	@Autowired
	private UploadUtil uploadUtil;

	@Value("${server.servlet.context-path}")
	private String serverName;

	@GetMapping("/serverTime.do")
	public Map<String, Object> serverTime() {
		return controllerTool.success(LocalDateTime.now());
	}

	@UserLoginToken
	@PostMapping("/upfile.do")
	public Map<String, Object> upFile(@RequestParam("file") MultipartFile file, String upType) {
		try {
			if (file != null && !file.isEmpty()) {
				String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
				String oldName = fileName;
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				String newFileName = UUID.randomUUID().toString() + suffix;
				fileName = uploadUtil.saveFileToDisk(file.getInputStream(), newFileName);
				if (StringUtils.isNotBlank(upType) && upType.equals("layedit")){
					return layEditUpFileResult(serverName + fileName, oldName);
				}else if (StringUtils.isNotBlank(upType) && upType.equals("wangEditor")){
					return wangEditorUpFileResult(serverName + fileName, oldName);
				}
				return controllerTool.success(fileName);
			}
		} catch (IOException e) {
			return controllerTool.error("107", "上传文件失败！");
		}
		return controllerTool.error("100", "上传文件为空！");
	}

	private Map<String, Object> wangEditorUpFileResult(String fileName, String oldName) {
		HashMap<String, Object> resultMap = new HashMap<>();
		List<String> filePaths = new ArrayList();
		filePaths.add(fileName);
		resultMap.put("errno", 0);
		resultMap.put("data", filePaths);
		return resultMap;
	}

	private HashMap<String, Object> layEditUpFileResult(String fileName, String oldName){
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> resultFile = new HashMap<>();
		resultFile.put("src", fileName);
		resultFile.put("title", oldName);
		resultMap.put("msg", "上传成功");
		resultMap.put("code", 0);
		resultMap.put("data", resultFile);
		return resultMap;
	}

	@UserLoginToken
	@PostMapping("/upfileWithOldName.do")
	public Map<String, Object> upfileWithOldName(@RequestParam("file") MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
				String oldName = fileName;
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				String newFileName = UUID.randomUUID().toString() + suffix;
				fileName = uploadUtil.saveFileToDisk(file.getInputStream(), newFileName);

				Map<String, String> fileNameMap = new HashMap<String, String>();
				fileNameMap.put("oldName", oldName);
				fileNameMap.put("newName", fileName);

				return controllerTool.success(fileNameMap);
			}
		} catch (IOException e) {
			return controllerTool.error("107", "上传文件失败！");
		}
		return controllerTool.error("100", "上传文件为空！");
	}

}
