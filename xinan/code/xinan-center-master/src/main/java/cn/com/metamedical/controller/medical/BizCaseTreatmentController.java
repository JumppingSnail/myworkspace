package cn.com.metamedical.controller.medical;


import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.*;
import cn.com.metamedical.service.ICaseTreatmentService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Allen
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/bizCaseTreatment")
public class BizCaseTreatmentController {

    @Resource
    private ControllerTool controllerTool;

    @Resource
    private ICaseTreatmentService caseTreatmentService;


    @UserLoginToken
    @PostMapping("/saveCaseTreatment.do")
    public Map<String, Object> saveCaseTreatment(HttpServletRequest request, @RequestBody CaseTreatmentSaveBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        String result = caseTreatmentService.saveCaseTreatment(currentUser, bean);
        return controllerTool.responseToClient(result);
    }

    @UserLoginToken
    @GetMapping("/getCaseTreatment.do")
    public Map<String, Object> getCaseTreatment(HttpServletRequest request, MedicalCaseDelBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        return controllerTool.responseToClient(caseTreatmentService.getCaseTreatment(currentUser, bean));
    }

    @UserLoginToken
    @GetMapping("/queryTreatmentDrugs.do")
    public Map<String,Object> queryTreatmentDrugs(HttpServletRequest request, CaseTreatmentDelBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        List<TreatmentDrugResult> result = caseTreatmentService.queryTreatmentDrugs(currentUser, bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", result.size());
        resMap.put("data", result);
        return resMap;
    }

    @UserLoginToken
    @GetMapping("/queryTreatmentList.do")
    public Map<String,Object> queryTreatmentList(HttpServletRequest request, CaseTreatmentDelBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.isNotBlank(bean.getId()))
            result = caseTreatmentService.queryTreatmentList(currentUser, bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", result.size());
        resMap.put("data", result);
        return resMap;
    }
}

