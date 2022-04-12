package cn.com.metamedical.controller.medical;


import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.medical.MedicalCaseDelBean;
import cn.com.metamedical.bean.medical.MedicalCaseSaveBean;
import cn.com.metamedical.bean.medical.MedicalSearchBean;
import cn.com.metamedical.bean.medical.StsSearchBean;
import cn.com.metamedical.service.IBizMedicalCaseService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
@RequestMapping("/bizMedicalCase")
public class BizMedicalCaseController {

    @Resource
    private IBizMedicalCaseService medicalCaseService;

    @Resource
    private ControllerTool controllerTool;

    @UserLoginToken
    @GetMapping("/getMedicalCaseList.do")
    public Map<String, Object> getMedicalCaseList(MedicalSearchBean bean) {
        IPage<Map<String, Object>> page = medicalCaseService.getMedicalCaseList(bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }

    @UserLoginToken
    @PostMapping("/saveMedicalCase.do")
    public Map<String, Object> saveMedicalCase(HttpServletRequest request,@RequestBody MedicalCaseSaveBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        String result = medicalCaseService.saveMedicalCase(currentUser, bean);
        return controllerTool.responseToClient(result);
    }


    @UserLoginToken
    @GetMapping("/getMedicalCase.do")
    public Map<String, Object> getMedicalCase(HttpServletRequest request, MedicalCaseSaveBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        return controllerTool.responseToClient(medicalCaseService.getMedicalCase(currentUser, bean));
    }

    @UserLoginToken
    @PostMapping("/deleteMedicalCase.do")
    public Map<String, Object> deleteMedicalCase(HttpServletRequest request, MedicalCaseDelBean bean) {
        String result = medicalCaseService.deleteMedicalCase(bean);
        if (result.equals("404")) return controllerTool.responseToClient("404", "不存在数据");
        return controllerTool.responseToClient(result);
    }

    @UserLoginToken
    @GetMapping("/getCaseInfoCount.do")
    public Map<String, Object> getCaseInfoCount(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        return controllerTool.responseToClient(medicalCaseService.getCaseInfoCount(currentUser));
    }

    @UserLoginToken
    @GetMapping("/getDiseaseCount.do")
    public Map<String, Object> getDiseaseCount(HttpServletRequest request, StsSearchBean searchBean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        return medicalCaseService.getDiseaseCount(currentUser, searchBean);
    }


    @UserLoginToken
    @GetMapping("/getMedicalCaseCountList.do")
    public Map<String, Object> getMedicalCaseCountList(StsSearchBean searchBean) {
        IPage<Map<String, Object>> page = medicalCaseService.getMedicalCaseCountList(searchBean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }

    @UserLoginToken
    @GetMapping("/getTreatmentCountList.do")
    public Map<String, Object> getCaseTreatmentCountList(StsSearchBean searchBean) {
        IPage<Map<String, Object>> page = medicalCaseService.getTreatmentCountList(searchBean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }

    @UserLoginToken
    @GetMapping("/getTreatmentDrugsCountList.do")
    public Map<String, Object> getTreatmentDrugsCountList(StsSearchBean searchBean) {
        IPage<Map<String, Object>> page = medicalCaseService.getTreatmentDrugsCountList(searchBean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }


}

