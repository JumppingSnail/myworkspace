package cn.com.metamedical.controller.comm;

import cn.com.metamedical.bean.comm.DictSearchBean;
import cn.com.metamedical.model.entity.BaseParamDict;
import cn.com.metamedical.service.IBaseParamDictService;
import cn.com.metamedical.util.comm.ControllerTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@RestController
@RequestMapping("dict")
public class BaseParamDictController {

    @Resource
    private ControllerTool controllerTool;

    @Resource
    private IBaseParamDictService baseParamDictService;

    @RequestMapping("/dictList.do")
    public Map<String, Object> queryDictList(DictSearchBean searchBean){
        List<BaseParamDict> result = baseParamDictService.queryDictList(searchBean);
        return controllerTool.responseToClient(result);
    }

}
