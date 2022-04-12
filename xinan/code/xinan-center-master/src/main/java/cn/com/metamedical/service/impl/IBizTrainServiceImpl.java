package cn.com.metamedical.service.impl;

import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.bean.train.TrainDeleteBean;
import cn.com.metamedical.bean.train.TrainSaveBean;
import cn.com.metamedical.bean.train.TrainSearchBean;
import cn.com.metamedical.mapper.BizTrainHospitalMapper;
import cn.com.metamedical.mapper.BizTrainMapper;
import cn.com.metamedical.mapper.BizTrainUserMapper;
import cn.com.metamedical.model.entity.*;
import cn.com.metamedical.service.IBizTrainService;
import cn.com.metamedical.util.tool.CommonTool;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 　　* @author YY Shen
 * 　　* @date $ $
 */
@Service
public class IBizTrainServiceImpl implements IBizTrainService {

    @Resource
    private CommonTool commonTool;

    @Resource
    private BizTrainMapper bizTrainMapper;

    @Resource
    private BizTrainUserMapper trainUserMapper;

    @Resource
    private BizTrainHospitalMapper trainHospitalMapper;

    @Override
    public IPage<Map<String, Object>> getBizTrainList(TrainSearchBean searchBean) {
        IPage<Map<String, Object>> page = new Page<>(searchBean.getPage(), searchBean.getLimit());
        return bizTrainMapper.getBizTrainList(page, searchBean.getTrainSubjectSearchLike(), searchBean.getTrainState());
    }

    @Override
    @Transactional
    public String saveTrain(UserBean user, TrainSaveBean item) {
        if (StringUtils.isNotBlank(item.getId())){
            BizTrain bizTrain = bizTrainMapper.selectById(item.getId());
            if (Objects.isNull(bizTrain)) return "404";
            BeanUtils.copyProperties(item, bizTrain);
            bizTrain.setUpdateTime(LocalDateTime.now());
            if (StringUtils.isNotBlank(item.getTrainContent())){
                String newContent = item.getTrainContent().replace("<img", "<img class='show-content' ");
                bizTrain.setTrainContent(newContent);
            }
            //维护培训对象
            maintainTrainObj(item.getObjectType(), item.getTrainMapIds(), bizTrain.getId());
            bizTrainMapper.updateById(bizTrain);
        }else {
            String trainId = commonTool.genTableId();
            item.setId(trainId);
            item.setCreateDate(LocalDate.now());
            item.setCreateUserName(user.getRealName());
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateUserId(user.getUserId());
            if (StringUtils.isNotBlank(item.getTrainContent())){
                String newContent = item.getTrainContent().replace("<img", "<img class='show-content' ");
                item.setTrainContent(newContent);

            }
            //维护培训对象
            maintainTrainObj(item.getObjectType(), item.getTrainMapIds(), trainId);
            bizTrainMapper.insert(item);
        }

        return "ok";
    }

    public void maintainTrainObj(String trainType, String maps, String trainId){
        if (StringUtils.isBlank(maps)) return;
        List<String> mapList = Arrays.stream(maps.split(",")).collect(Collectors.toList());
        if ("2".equals(trainType)){
            trainHospitalMapper.delete(new LambdaQueryWrapper<BizTrainHospital>().eq(BizTrainHospital::getTrainId, trainId));
            for (String orgId : mapList) {
                BizTrainHospital bizTrainHospital = new BizTrainHospital();
                bizTrainHospital.setId(commonTool.genTableId());
                bizTrainHospital.setOrgId(orgId);
                bizTrainHospital.setTrainId(trainId);
                trainHospitalMapper.insert(bizTrainHospital);
            }
        }else if ("3".equals(trainType)){
            trainUserMapper.delete(new LambdaQueryWrapper<BizTrainUser>().eq(BizTrainUser::getTrainId, trainId));
            for (String userId : mapList) {
                BizTrainUser trainUser = new BizTrainUser();
                trainUser.setId(commonTool.genTableId());
                trainUser.setTrainId(trainId);
                trainUser.setUserId(userId);
                trainUserMapper.insert(trainUser);
            }
        }

    }

    @Override
    public Map<String, Object> queryTrainMap(UserBean currentUser, TrainDeleteBean bean) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "ok");
        String trainMapIds = "";
        String trainMap = "";
        if (StringUtils.isBlank(bean.getTrainId()) || StringUtils.isBlank(bean.getSubjectType()) ) return resultMap;
        if ("2".equals(bean.getSubjectType())){//指定医院
            List<BaseOrg> bizTrainHospitals = trainHospitalMapper.selectOrgListByTrainId(bean.getTrainId());
            trainMapIds = bizTrainHospitals.stream().map(BaseOrg::getId).collect(Collectors.joining(","));
            trainMap = bizTrainHospitals.stream().map(BaseOrg::getOrgName).collect(Collectors.joining(","));

        }else if ("3".equals(bean.getSubjectType())){//指定医生
            List<SysUser> bizTrainUsers = trainHospitalMapper.selectUserListByTrainId(bean.getTrainId());
            trainMapIds = bizTrainUsers.stream().map(SysUser::getId).collect(Collectors.joining(","));
            trainMap = bizTrainUsers.stream().map(this::codeNameJoin).collect(Collectors.joining(","));
        }

        resultMap.put("trainMapIds", trainMapIds);
        resultMap.put("trainMap", trainMap);

        return resultMap;
    }

    private String codeNameJoin(SysUser item){
        String jsonMes = item.getUserRealName();
        if (StringUtils.isNotBlank(item.getUserRealName())){
            jsonMes = item.getUserRealName() + "-" + jsonMes;
        }
        return jsonMes;
    }

    @Override
    @Transactional
    public String deleteTrain(UserBean currentUser, TrainDeleteBean bean) {
        if (StringUtils.isBlank(bean.getTrainId())) return "400";
        BizTrain bizTrain = bizTrainMapper.selectById(bean.getTrainId());
        if (Objects.isNull(bizTrain)) return "404";

        trainHospitalMapper.delete(new LambdaQueryWrapper<BizTrainHospital>()
                .eq(BizTrainHospital::getTrainId, bean.getTrainId()));
        trainUserMapper.delete(new LambdaQueryWrapper<BizTrainUser>()
                .eq(BizTrainUser::getTrainId, bean.getTrainId()));
        bizTrainMapper.deleteById(bean.getTrainId());
        return "ok";
    }

    @Override
    public String updateTrainState(UserBean currentUser, TrainDeleteBean bean) {
        if (StringUtils.isBlank(bean.getTrainId())) return "400";
        BizTrain bizTrain = bizTrainMapper.selectById(bean.getTrainId());
        if (Objects.isNull(bizTrain)) return "404";
        if ("3".equals(bizTrain.getTrainState())) return "任务已结束";
        bizTrain.setTrainState(bean.getTrainState());
        bizTrainMapper.updateById(bizTrain);
        return "ok";
    }

    @Override
    public String updateEndTrainTask() {
        //扫描培训记录中所有的进行中任务
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<BizTrain> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BizTrain::getTrainState, "2").orderByDesc(BizTrain::getCreateDate);
        List<BizTrain> bizTrains = bizTrainMapper.selectList(lambdaQueryWrapper);
        List<String> filterCollect = bizTrains.stream().filter(item -> now.isAfter(item.getEndTime())).map(BizTrain::getId).collect(Collectors.toList());
        if (filterCollect.size() <= 0) return "暂无任务需要结束";
        LambdaQueryWrapper<BizTrain> updateQuery = new LambdaQueryWrapper<>();
        updateQuery.in(BizTrain::getId, filterCollect).ne(BizTrain::getTrainState, "4");
        BizTrain bizTrain = new BizTrain();
        bizTrain.setTrainState("4");
        bizTrainMapper.update(bizTrain, updateQuery);
        return "ok";
    }

}
