package com.polycis.main.controller.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.netflix.discovery.converters.Auto;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.App;
import com.polycis.main.entity.AppOrgRelation;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.vo.AppVo;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.polycis.main.service.db1.IAppService;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IDownlinkDataService;
import com.polycis.main.service.db2.IDevUnionDeviceService;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/monitor")
public class MonitorCenterController {

    @Autowired
    private IAppService iAppService;

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private IDownlinkDataService iDownlinkDataService;

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IDevUnionDeviceService iDevUnionDeviceService;

    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;

    @Autowired
    private IMybatisPlusDB2Service iMybatisPlusDB2Service;

    @ApiOperation(value = "查看基本信息", notes = "查看基本信息")
    @RequestMapping(value = "/afewinfo", method = RequestMethod.POST)
    public ApiResult afewinfo() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();


        EntityWrapper<AppOrgRelation> appOrgRelationEntityWrapper = new EntityWrapper<>();
        appOrgRelationEntityWrapper.eq("organization_id", currentUser.getOrg());
        Integer appCount = iAppOrgRelationService.selectCount(appOrgRelationEntityWrapper);

        Integer deviceCount = iDeviceService.selectOrgCount(currentUser);

        // 查询今日新增设备数
        Integer todayDevCount = iDeviceService.selectTodayCount(currentUser);

        // 查询组织下的应用eui集合
        List<String> list = iAppOrgRelationService.selectAppEui(currentUser.getOrg());

        Integer onlineCount = iDevUnionDeviceService.selectOnLineDevice(currentUser, list);


        // 组织下 的 设备eui集合
        List<String> list2 =  iDeviceService.selectDeviceList(currentUser.getOrg());

        // 查询今日下发数据量
        Integer todayDownDateCount =iMybatisPlusDB3Service.selectDownDataCount(list2);

        // 今日推送消息数
        Integer todayUpDataCount =iMybatisPlusDB3Service.selectUpDataCount(list2);

        Map<String, Integer> map = new HashMap<>();
        map.put("appCount", appCount);
        map.put("deviceCount", deviceCount);
        map.put("todayDevCount", todayDevCount);
        map.put("todayDownDateCount", todayDownDateCount);
        map.put("onlineCount",onlineCount);
        map.put("todayUpDataCount",todayUpDataCount);
        apiResult.setData(map);
        return apiResult;
    }


    @ApiOperation(value = "查看应用信息集合", notes = "查看应用信息集合")
    @RequestMapping(value = "/app", method = RequestMethod.POST)
    public ApiResult app() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        List<AppVo> list= iAppService.seletMonitorApp(currentUser.getOrg());

        list.forEach(appVo -> {
          AppVo appVo1=  iMybatisPlusDB2Service.selectAppVo(appVo);
          if(null ==appVo1.getDeviceOnlineRate()){
              appVo1.setDeviceOnlineRate("0.0");
          }
            appVo.setDeviceOnlineRate(appVo1.getDeviceOnlineRate());
        });

        list.forEach(appVo -> {
            List<String> strings = iDeviceService.selectDevList(appVo.getId());
            Integer count=  iMybatisPlusDB3Service.selectAppAlarm(strings);
            appVo.setAlarmCount(count);
        });

        apiResult.setData(list);
        return apiResult;
    }




    @ApiOperation(value = "查看基本信息", notes = "查看基本信息")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ApiResult test() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();
        // Integer onlineCount = iDevUnionDeviceService.selectOnLineDevice(currentUser);
        List<String> list = iAppOrgRelationService.selectAppEui(currentUser.getOrg());

        Integer onlineCount = iDevUnionDeviceService.selectOnLineDevice(currentUser, list);
        apiResult.setData(onlineCount);
        return apiResult;
    }
}
