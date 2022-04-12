package cn.com.metamedical.controller.base;

import cn.com.metamedical.bean.sys.SysSearchBean;
import cn.com.metamedical.model.entity.BaseOrg;
import cn.com.metamedical.service.IBaseOrgService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.comm.ControllerTool;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@RestController
@RequestMapping("org")
public class BaseOrgController {

    @Resource
    private ControllerTool controllerTool;

    @Resource
    private IBaseOrgService baseOrgService;

    @UserLoginToken
    @GetMapping("/list.do")
    public Map<String, Object> getOrgList(HttpServletRequest request, SysSearchBean bean) {
        IPage<Map<String, Object>> page = baseOrgService.getOrgList(bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }

    @UserLoginToken
    @GetMapping("/getNoPageList.do")
    public Map<String, Object> getNoPageList(HttpServletRequest request, SysSearchBean bean) {
        Map<String, Object> resMap = new HashMap<>();
        List<BaseOrg> result = baseOrgService.getNoPageList(bean);
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", 0);
        resMap.put("data", result);
        return resMap;
    }

    @UserLoginToken
    @PostMapping("/saveOrg.do")
    public Map<String, Object> saveOrg(HttpServletRequest request, @RequestBody BaseOrg entity) {
        String result = baseOrgService.saveOrg(entity);
        return controllerTool.responseToClient(result);
    }

}
