package com.polycis.main.controller.app;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.app.AppFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.log.MyLog;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.*;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.entity.db3.DevDownDataPO;
import com.polycis.main.entity.db3.DevUpDataPO;
import com.polycis.main.entity.vo.QueryTimePO;
import com.polycis.main.service.db1.*;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/*
 * @author qiaokai
 * @since 2019-04-19
*/
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
    private IMybatisPlusDB2Service iMybatisPlusDB2Service;

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;


    @RoleOfAdmin
    @MyLog(describe = "添加应用")
    @ApiOperation(value = "添加应用", notes = "添加应用接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResult addApp(@RequestBody App app) {
        // 用户id即组织id

        ApiResult apiResult = new ApiResult<>();
        app.setAppEui(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        ApiResult apiResult1 = appFeignClient.create(app);
        LOG.info(apiResult1.getMsg());
        if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
            // 将用户信息的prg 添加到关联表中
            LOG.info("这是资源返回的data"+apiResult1.getData().toString());
            app.setAppKey(apiResult1.getData().toString());
            boolean b = iAppService.addApp(app);
            if (b) {
                apiResult.setSub_code(app.getId());
                return apiResult;
            }
            LOG.info("添加应用失败:{}", app.toString());
        }

        return apiResult1;

    }


    @ApiOperation(value = "查看应用列表", notes = "查看应用列表")
    @RequestMapping(value = "/applist", method = RequestMethod.POST)
    public ApiResult applist(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Map<String, Object> data = requestVO.getData();
        App app = JSON.parseObject(JSON.toJSONString(data), App.class);
        Page<App> page = iAppService.queryAppList(currentPage, pageSize, app);
       // 塞入http/mq信息
        page.getRecords().forEach(s->{
            App app2 = iMybatisPlusDB2Service.appInfo(s);
            s=app2;
        });
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;
    }

    @ApiOperation(value = "查看应用详情", notes = "查看应用详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ApiResult info(@RequestBody App app) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        App app1 = iAppService.selectById(app);

        // aop切入,直接用plus的service切不进去有待研究
        App app2 = iMybatisPlusDB2Service.appInfo(app1);

        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(app2);
        return apiResult;
    }

    @RoleOfAdmin
    @MyLog(describe = "更新应用")
    @ApiOperation(value = "更新应用", notes = "更新应用")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody App app) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        // 应用更新接口只更新 应用的http
        ApiResult apiResult1 = appFeignClient.update(app);

        System.out.println(apiResult1.toString());
        if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
            // 在此存在一个问题是,用户可以更改自己应用的组织id到其他组织上,是app上org的冗余字段发生问题,但这个冗余字段后续应该不用,时间仓促后续再处理
            iAppService.updateById(app);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.ERROR.getValue());
        apiResult.setCode(CommonCode.ERROR.getKey());
        return apiResult;

    }

    @RoleOfAdmin
    @MyLog(describe = "删除应用")
    @ApiOperation(value = "删除应用", notes = "删除应用")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody App app) {

        App app1 = iAppService.selectById(app);

        ApiResult apiResult = new ApiResult<>();
            List<Device> devices = iDeviceService.selectList(new EntityWrapper<Device>()
                    .eq("is_delete", MainConstants.UN_DELETE)
                    .eq("app_id", app.getId()));
            if (devices.size() > 0) {
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("应用下有设备,请先删除应用下的设备");
                return apiResult;
            }
            //接入层来判断到底能删不能删,删不掉自然我这就不用走查询应用下设备逻辑了

            ApiResult apiResult1 = appFeignClient.delete(app1.getAppEui().toString());
            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
                iAppService.deleteApp(app);
                return apiResult;
            }
            return apiResult1;

    }

    @ApiOperation(value = "应用内设备列表模糊查询", notes = "应用内设备列表模糊查询")
    @RequestMapping(value = "/devicelist", method = RequestMethod.POST)
    public ApiResult devicelist(@RequestBody RequestVO requestVO) {
        Page<Device> page = iDeviceService.selectAppDeviceList(requestVO);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;

    }


    @ApiOperation(value = "应用内上行数据模糊查询", notes = "应用内上行数据模糊查询")
    @RequestMapping(value = "/updata", method = RequestMethod.POST)
    public ApiResult updata(@RequestBody RequestVO requestVO) {

        ApiResult apiResult = new ApiResult<>();

        PageInfoVO pageInfo = requestVO.getPageInfo();
        QueryTimePO queryTimePO = JSON.parseObject(JSON.toJSONString(requestVO.getData()), QueryTimePO.class);

        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", queryTimePO.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);

        // 查询上行数据
        Page<DevUpDataPO> devUpDataPOPage = iMybatisPlusDB3Service.selectAppUpData(pageInfo, queryTimePO, objects);
        apiResult.setData(devUpDataPOPage);
        return apiResult;

    }

    @ApiOperation(value = "应用内下行数据模糊查询", notes = "应用内下行数据模糊查询")
    @RequestMapping(value = "/downdata", method = RequestMethod.POST)
    public ApiResult downdata(@RequestBody RequestVO requestVO) {

        ApiResult apiResult = new ApiResult<>();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        QueryTimePO queryTimePO = JSON.parseObject(JSON.toJSONString(requestVO.getData()), QueryTimePO.class);

        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", queryTimePO.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);
        // 查询上行数据
        Page<DevDownDataPO> devDownDataPOPage = iMybatisPlusDB3Service.selectAppDownData(pageInfo, queryTimePO, objects);
        apiResult.setData(devDownDataPOPage);
        return apiResult;
    }


    @ApiOperation(value = "应用内设备告警记录", notes = "应用内设备告警记录")
    @RequestMapping(value = "/devalarm", method = RequestMethod.POST)
    public ApiResult devalarm(@RequestBody RequestVO requestVO) {
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

        EntityWrapper<AppOrgRelation> appOrgRelationEntityWrapper = new EntityWrapper<>();
        appOrgRelationEntityWrapper.setSqlSelect("app_id");
        List<Object> objects = iAppOrgRelationService.selectObjs(appOrgRelationEntityWrapper);

        EntityWrapper<App> appEntityWrapper = new EntityWrapper<>();
        if (objects.size() == 0) {
            appEntityWrapper.eq("id", -989898989);
        } else {
            appEntityWrapper.in("id", objects);
        }

        // appEntityWrapper.setSqlSelect("id,name");
        List<App> apps = iAppService.selectList(appEntityWrapper);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(apps);
        return apiResult;

    }

    @ApiOperation(value = "应用设备拓扑", notes = "应用设备拓扑")
    @RequestMapping(value = "/topo", method = RequestMethod.POST)
    public ApiResult topo(@RequestBody App app) {
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


}

