package com.polycis.main.controller.monitor;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.*;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.service.db1.*;
import com.polycis.main.service.db2.IDevUnionDeviceService;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IDevDataWarnService;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private IUsersService iUsersService;

    @Autowired
    private IOrgService iOrgService;

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


    @Autowired
    private IDevDataWarnService iDevDataWarnService;

   /* @ApiOperation(value = "查看基本信息", notes = "查看基本信息")
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
*/

    @ApiOperation(value = "查看用户和应用总数", notes = "查看用户和应用总数")
    @RequestMapping(value = "/indexinfo", method = RequestMethod.POST)
    public ApiResult indexinfo() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();

        int appCount = iAppService.selectCount(new EntityWrapper<App>().eq("is_delete", MainConstants.UN_DELETE));
        int userCOunt = iOrgService.selectCount(new EntityWrapper<Org>().eq("is_delete", MainConstants.UN_DELETE));
        Map<String, Integer> map = new HashMap<>();
        map.put("appCount",appCount);
        map.put("userCOunt",userCOunt);
        apiResult.setData(map);
        return apiResult;
    }

    @ApiOperation(value = "查看设备总数信息", notes = "查看设备总数信息")
    @RequestMapping(value = "/devicecount", method = RequestMethod.POST)
    public ApiResult devicecount() {

        ApiResult apiResult = new ApiResult<>();
        Object devWarnCount = iMybatisPlusDB3Service.selectDevWarnCount();
        Integer onlineCounti = iMybatisPlusDB3Service.selectDevOnLineCount();

        int devCount = iMybatisPlusDB3Service.selectDevCount();
        Map<String, Integer> map = new HashMap<>();
        map.put("devWarnCount",Integer.parseInt(devWarnCount.toString()));
        map.put("onlineCounti",onlineCounti);
        map.put("devCount",devCount);
        apiResult.setData(map);
        return apiResult;
    }


    @ApiOperation(value = "查看网关总数信息", notes = "查看网关总数信息")
    @RequestMapping(value = "/gatewaycount", method = RequestMethod.POST)
    public ApiResult gatewaycount() {

        ApiResult apiResult = new ApiResult<>();
        Map<String, Integer> map = new HashMap<>();
        map.put("gatewayCount",3);
        map.put("onlinegatewayCounti",3);
        map.put("warnCount",0);
        apiResult.setData(map);
        return apiResult;
    }


    @ApiOperation(value = "告警状态占比", notes = "查看网关总数信息")
    @RequestMapping(value = "warnstatus", method = RequestMethod.POST)
    public ApiResult warnstatus() {

        ApiResult apiResult = new ApiResult<>();
        Map<String, Integer> map = new HashMap<>();
        // status 0未处理 1处理中
        Integer devWarnCount0 = iMybatisPlusDB3Service.selectDevWarnCount0();
        Integer devWarnCount1 = iMybatisPlusDB3Service.selectDevWarnCount1();
        map.put("devWarnCount0",devWarnCount0);
        map.put("devWarnCount1",devWarnCount1);
        apiResult.setData(map);
        return apiResult;
    }

    @ApiOperation(value = "首页告警表", notes = "首页告警表")
    @RequestMapping(value = "warnlist", method = RequestMethod.POST)
    public ApiResult warnlist(@RequestBody RequestVO requestVO) {

        ApiResult apiResult = new ApiResult<>();
        Map<String, Integer> map = new HashMap<>();
        // status 0未处理 1处理中
        Page<DevDataWarn> page0 =iMybatisPlusDB3Service.selectDevWarnPage(requestVO);

        // 查询库1
        Page<DevDataWarn> page =iMybatisPlusDB3Service.selectDevWarnPageName(page0);

        apiResult.setData(page);
        return apiResult;
    }


    @ApiOperation(value = "设备网络接入类型占比", notes = "设备网络接入类型占比")
    @RequestMapping(value = "devtype", method = RequestMethod.POST)
    public ApiResult devtype() {

        ApiResult apiResult = new ApiResult<>();
        Map<String, Integer> map = new HashMap<>();
        // status 0未处理 1处理中
        EntityWrapper<Device> wrapper = new EntityWrapper<>();

        wrapper.setSqlSelect("platform as platform, count(id) as count")
                .eq("is_delete",MainConstants.UN_DELETE)
                .groupBy("platform");
        List<Map<String, Object>> maps = iDeviceService.selectMaps(wrapper);
        maps.forEach(s-> System.out.println(s.toString()));
        apiResult.setData(maps);
        return apiResult;
    }


    @RoleOfAdmin
    @ApiOperation(value = "近七日上下行数据统计", notes = "近七日上下行数据统计")
    @RequestMapping(value = "aweekdata", method = RequestMethod.POST)
    public ApiResult aweekdata() {
        List<Map<String, Object>> list = iMybatisPlusDB3Service.selectAWeekdataOss();
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(list);
        return  apiResult;
    }


}


