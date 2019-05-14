/*
package com.polycis.main.controller.app;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.app.AppFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.*;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.entity.db3.DevDownDataPO;
import com.polycis.main.entity.db3.DevUpDataPO;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.polycis.main.service.db1.IAppService;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import com.polycis.main.service.db2.IDevHttpService;
import com.polycis.main.service.db2.IDevMqQueueService;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author qiaokai
 * @since 2019-04-19
 *//*

@RestController
@RequestMapping("/app")
public class AppController {
    protected static Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IAppService iAppService;

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private AppFeignClient appFeignClient;
    @Autowired
    private IDevMqQueueService iDevMqQueueService;

    @Autowired
    private IDevHttpService iDevHttpService;

    @Autowired
    private IMybatisPlusDB2Service iMybatisPlusDB2Service;

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;

    @Autowired
    private IProductService iProductService;


    @ApiOperation(value = "添加应用", notes = "添加应用接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResult addApp(@RequestBody App app) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            // 服务接口应该做应用eui唯一校验,如果让应用层做也行,但这会是个漏洞,因为应用层只是负责传递而已
            EntityWrapper<App> appEntityWrapper = new EntityWrapper<>();
            appEntityWrapper.eq("app_eui", app.getAppEui());
            List<App> apps = iAppService.selectList(appEntityWrapper);
            if (apps.size() > 0) {
                apiResult.setMsg("应用eui已被占用");
                apiResult.setCode(CommonCode.PARAMETER_INVALID.getKey());
                return apiResult;
            }
            ApiResult apiResult1 = appFeignClient.create(app);
            LOG.info(apiResult1.getMsg());
            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
                app.setOrganizationId(currentUser.getOrg());
                boolean b = iAppService.addApp(app, currentUser);
                if (b) {
                    apiResult.setSub_code(app.getId());
                    return apiResult;
                }
                LOG.info("添加应用失败:{},用户:{}", app.toString(), currentUser.toString());
            }

            return apiResult1;

        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }
    }


    @ApiOperation(value = "查看应用列表", notes = "查看应用列表")
    @RequestMapping(value = "/applist", method = RequestMethod.POST)
    public ApiResult addApp(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();

        Map<String, Object> data = requestVO.getData();
        App app = JSON.parseObject(JSON.toJSONString(data), App.class);
        Page<App> page = iAppService.queryAppList(currentPage, pageSize, currentUser, app);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;
    }

    @ApiOperation(value = "查看应用详情", notes = "查看应用详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ApiResult info(@RequestBody App app) {
        // 仿照shiro实现获取user
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        App app1 = iAppService.selectById(app);

        int i = iDeviceService.selectCount(new EntityWrapper<Device>()
                .eq("is_delete", MainConstants.UN_DELETE)
                .eq("app_id", app.getId()));
        app1.setPicturepath(String.valueOf(i));

        // aop切入,直接用plus的service切不进去有待研究
        App app2 = iMybatisPlusDB2Service.appInfo(app1);

        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(app2);
        return apiResult;
    }


    // 想走restful风格,奈何前端不允许,只好用动词
    @ApiOperation(value = "更新应用", notes = "更新应用")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody App app) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        if (currentUser.getRole().contains(MainConstants.SYS)) {


            // 应用更新接口只更新 应用的http
            ApiResult apiResult1 = appFeignClient.update(app);
            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
                // 在此存在一个问题是,用户可以更改自己应用的组织id到其他组织上,是app上org的冗余字段发生问题,但这个冗余字段后续应该不用,时间仓促后续再处理
                iAppService.updateById(app);
                return apiResult;
            }
            apiResult.setMsg(CommonCode.ERROR.getValue());
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;
        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }
    }

    @ApiOperation(value = "删除应用", notes = "删除应用")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            List<Device> devices = iDeviceService.selectList(new EntityWrapper<Device>()
                    .eq("is_delete", MainConstants.UN_DELETE)
                    .eq("app_id", app.getId()));
            if(devices.size()>0){
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("应用下有设备,请先删除应用下的设备");
                return apiResult;
            }
            //接入层来判断到底能删不能删,删不掉自然我这就不用走查询应用下设备逻辑了
            ApiResult apiResult1 = appFeignClient.delete(app.getAppEui());
            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
                iAppService.deleteApp(currentUser, app);
                return apiResult;
            }
            return apiResult1;

        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }

    }

    @ApiOperation(value = "应用内设备列表模糊查询", notes = "应用内设备列表模糊查询")
    @RequestMapping(value = "/devicelist", method = RequestMethod.POST)
    public ApiResult devicelist(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        Page<Device> page = iDeviceService.selectAppDeviceList(requestVO, currentUser);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;

    }


    @ApiOperation(value = "应用内产品数量占比", notes = "应用内产品数量占比")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ApiResult product(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        List<Map<String, Object>> list = iAppService.selectProduct(currentUser, app);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(list);
        return apiResult;

    }

    @ApiOperation(value = "应用设备拓扑", notes = "应用设备拓扑")
    @RequestMapping(value = "/topo", method = RequestMethod.POST)
    public ApiResult topo(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("app_id", app.getId());
        wrapper.eq("is_delete", MainConstants.UN_DELETE);
        List<Device> list = iDeviceService.selectList(wrapper);
        App app1 = iAppService.selectById(app);
        HashMap<String, Object> map = new HashMap<>();
        map.put("devlist", list);
        map.put("name", app1.getName());
        map.put("id", app1.getId());
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(map);
        return apiResult;

    }

    @ApiOperation(value = "设备在线率", notes = "设备在线率")
    @RequestMapping(value = "/devonline", method = RequestMethod.POST)
    public ApiResult devonline(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        App app1 = iAppService.selectById(app);
        List<Map<String, Integer>> list = iMybatisPlusDB2Service.selectDevonline(app1);
        apiResult.setData(list);
        return apiResult;
    }

    @ApiOperation(value = "应用上下行数据量占比", notes = "应用上下行数据量占比")
    @RequestMapping(value = "/updownrate", method = RequestMethod.POST)
    public ApiResult updownrate(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();

        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", app.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);

        Map<String, Object> map = iMybatisPlusDB3Service.selectAppUpDownDataCount(objects);
        apiResult.setData(map);
        return apiResult;
    }


    @ApiOperation(value = "测试", notes = "测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ApiResult test() {
        ApiResult apiResult = new ApiResult<>();
        List<String> list = iAppService.selctTest();

        return apiResult;
    }


    @ApiOperation(value = "应用内近七日上下行数据统计", notes = "应用内近七日上下行数据统计")
    @RequestMapping(value = "/aweekdata", method = RequestMethod.POST)
    public ApiResult aweekdata(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        List<String> uuidlist = iDeviceService.selectDevList(app.getId());

        List<Map<String, Object>> list = iMybatisPlusDB3Service.selectAWeekData(uuidlist);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(list);
        return apiResult;

    }

    @ApiOperation(value = "应用内上行数据模糊查询", notes = "应用内上行数据模糊查询")
    @RequestMapping(value = "/updata", method = RequestMethod.POST)
    public ApiResult updata(@RequestBody RequestVO requestVO) {

        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        App app = JSON.parseObject(JSON.toJSONString(requestVO.getData()), App.class);

      */
/*  // 查询产品id
        EntityWrapper<Product> wrapper = new EntityWrapper<>();
        if (null != app.getName() && !"".equals(app.getName())) {
            wrapper.like("name", app.getName());
        }
        wrapper.setSqlSelect("id");
        List<Object> list = iProductService.selectObjs(wrapper);*//*


        // 查询设备id
        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        if (null != app.getAppEui() && !"".equals(app.getAppEui())) {
            deviceEntityWrapper.like("device_uuid", app.getAppEui(), SqlLike.CUSTOM);
        }
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", app.getId());
        // deviceEntityWrapper.in("product_id", list);
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);

        // 查询上行数据
        Page<DevUpDataPO> devUpDataPOPage = iMybatisPlusDB3Service.selectAppUpData(objects, requestVO.getPageInfo());
        apiResult.setData(devUpDataPOPage);
        return apiResult;

    }

    @ApiOperation(value = "应用内下行数据模糊查询", notes = "应用内下行数据模糊查询")
    @RequestMapping(value = "/downdata", method = RequestMethod.POST)
    public ApiResult downdata(@RequestBody RequestVO requestVO) {

        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        App app = JSON.parseObject(JSON.toJSONString(requestVO.getData()), App.class);


        // 查询设备id
        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        if (null != app.getAppEui() && !"".equals(app.getAppEui())) {
            deviceEntityWrapper.like("device_uuid", app.getAppEui(), SqlLike.CUSTOM);
        }
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", app.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);

    */
/*    System.out.println("uuid is");
        objects.forEach(s-> System.out.println("uuid"+s.toString()));*//*

        // 查询上行数据
        Page<DevDownDataPO> devDownDataPOPage = iMybatisPlusDB3Service.selectAppDownData(objects, requestVO.getPageInfo());
        apiResult.setData(devDownDataPOPage);
        return apiResult;
    }


    @ApiOperation(value = "应用内设备告警记录", notes = "应用内设备告警记录")
    @RequestMapping(value = "/devalarm", method = RequestMethod.POST)
    public ApiResult devalarm(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        App app = JSON.parseObject(JSON.toJSONString(requestVO.getData()), App.class);
        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();

        // 用appEui来接收模糊查询参数(设备名称/设备uuid)
        if (null != app.getAppEui() && !"".equals(app.getAppEui())) {
            deviceEntityWrapper.andNew().like("device_uuid", app.getAppEui())
                    .or().like("name", app.getAppEui());
        }
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", app.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        // 查询设备uuid集合
        List<Object> list = iDeviceService.selectObjs(deviceEntityWrapper);

        Page<DevDataWarn> devDataWarnPage1 = iMybatisPlusDB3Service.selectPage(list, pageInfo);

        apiResult.setData(devDataWarnPage1);
        return apiResult;
    }

    @ApiOperation(value = "应用下拉列表", notes = "应用下拉列表")
    @RequestMapping(value = "/downlist", method = RequestMethod.POST)
    public ApiResult downlist() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        EntityWrapper<AppOrgRelation> appOrgRelationEntityWrapper = new EntityWrapper<>();
        appOrgRelationEntityWrapper.eq("organization_id", currentUser.getOrg());
        appOrgRelationEntityWrapper.setSqlSelect("app_id");
        List<Object> objects = iAppOrgRelationService.selectObjs(appOrgRelationEntityWrapper);

        EntityWrapper<App> appEntityWrapper = new EntityWrapper<>();
        if(objects.size()==0){
            appEntityWrapper.eq("id", -989898989);
        }else {
            appEntityWrapper.in("id", objects);
        }

        // appEntityWrapper.setSqlSelect("id,name");
        List<App> apps = iAppService.selectList(appEntityWrapper);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(apps);
        return apiResult;

    }


}

*/
