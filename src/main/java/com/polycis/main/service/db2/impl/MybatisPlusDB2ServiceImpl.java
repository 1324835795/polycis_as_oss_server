package com.polycis.main.service.db2.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.datasource.DBTypeEnum;
import com.polycis.main.common.datasource.DataSourceSwitch;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.db2.DevHttp;
import com.polycis.main.entity.db2.DevMqQueue;
import com.polycis.main.entity.db2.DevUnionDevice;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.entity.vo.AppVo;
import com.polycis.main.service.db2.IDevHttpService;
import com.polycis.main.service.db2.IDevMqQueueService;
import com.polycis.main.service.db2.IDevUnionDeviceService;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IDevDataWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class MybatisPlusDB2ServiceImpl implements IMybatisPlusDB2Service {

    @Autowired
    private IDevMqQueueService iDevMqQueueService;

    @Autowired
    private IDevHttpService iDevHttpService;

    @Autowired
    private IDevUnionDeviceService iDevUnionDeviceService;
    @Autowired
    private IDevDataWarnService iDevDataWarnService;

    @Override
    public App appInfo(App app) {

        // app的push_type存在应用层,向前端展示http/mq两个信息
        EntityWrapper<DevHttp> devHttpEntityWrapper = new EntityWrapper<>();

        devHttpEntityWrapper.eq("app_eui", app.getAppEui());
        // 不做非空判断了,因为不捣乱的情况下
        List<DevHttp> devHttps = iDevHttpService.selectList(devHttpEntityWrapper);
        if (devHttps.size() > 0) {
            app.setHttp(devHttps.get(0).getHttpName());
        }

        EntityWrapper<DevMqQueue> mqEntityWrapper = new EntityWrapper<>();
        mqEntityWrapper.eq("app_eui", app.getAppEui());
        // 2为优先级队列,魔法字算了
        mqEntityWrapper.eq("priority", 2);
        List<DevMqQueue> devMqQueues = iDevMqQueueService.selectList(mqEntityWrapper);

        if (devMqQueues.size() > 0) {
            app.setMq(devMqQueues.get(0).getQueueName());
        }


        return app;

    }

    @Override
    public List<Map<String, Integer>> selectDevonline(App app) {


        return iDevUnionDeviceService.selectDevonline(app);


    }

    @Override
    public Page<Device> putDevListInfo(Page<Device> devicePage1) {

        // 向接入层查询设备的状态与最近一次的数据上报时间
        devicePage1.getRecords().forEach(dev ->
                {
                    // 调用redis查询接入层数据库设备的缓存信息
                    //   ApiResult apiResult1 = redisFeignClient.get("a:" + dev.getDeviceUuid());
                    EntityWrapper<DevUnionDevice> deviceEntityWrapper1 = new EntityWrapper<>();
                    deviceEntityWrapper1.eq("dev_uuid", dev.getDeviceUuid());
                    try {
                        DevUnionDevice devUnionDevice = iDevUnionDeviceService.selectList(deviceEntityWrapper1).get(0);
                        dev.setStatus(devUnionDevice.getStatus());
                        dev.setReportTime(devUnionDevice.getReportTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return devicePage1;
    }

    @Override
    public AppVo selectAppVo(AppVo appVo) {

        return iDevUnionDeviceService.selectAppVo(appVo);
    }





}
