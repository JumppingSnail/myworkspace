package cn.com.metamedical.controller.train;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.train.TrainDeleteBean;
import cn.com.metamedical.bean.train.TrainSaveBean;
import cn.com.metamedical.bean.train.TrainSearchBean;
import cn.com.metamedical.service.IBizTrainService;
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
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@RestController
@RequestMapping("train")
public class BizTrainController {

    @Resource
    private ControllerTool controllerTool;

    @Resource
    private IBizTrainService bizTrainService;

    @UserLoginToken
    @GetMapping("/list.do")
    public Map<String, Object> getBizTrainList(HttpServletRequest request, TrainSearchBean bean) {
        IPage<Map<String, Object>> page = bizTrainService.getBizTrainList(bean);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", page.getTotal());
        resMap.put("data", page.getRecords());
        return resMap;
    }

    @UserLoginToken
    @PostMapping("/saveTrain.do")
    public Map<String, Object> saveTrain(HttpServletRequest request,@RequestBody TrainSaveBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        String result = bizTrainService.saveTrain(currentUser, bean);
        return controllerTool.responseToClient(result);
    }

    @UserLoginToken
    @GetMapping("/queryTrainMap.do")
    public Map<String, Object> queryTrainMap(HttpServletRequest request, TrainDeleteBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        Map<String, Object> result = bizTrainService.queryTrainMap(currentUser, bean);
        return result;
    }

    @UserLoginToken
    @PostMapping("/delete.do")
    public Map<String, Object> deleteTrain(HttpServletRequest request, TrainDeleteBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        String result = bizTrainService.deleteTrain(currentUser, bean);
        return controllerTool.responseToClient(result);
    }

    @UserLoginToken
    @PostMapping("/updateState.do")
    public Map<String, Object> updateState(HttpServletRequest request, TrainDeleteBean bean) {
        String token = request.getHeader(CommonConstant.TOKEN_KEY);
        UserBean currentUser = JvmCacheTool.getTokenInfo(token);
        String result = bizTrainService.updateTrainState(currentUser, bean);
        return controllerTool.responseToClient(result);
    }

}
