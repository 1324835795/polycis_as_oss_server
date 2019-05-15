package com.polycis.main.service.db3.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.datasource.DBTypeEnum;
import com.polycis.main.common.datasource.DataSourceSwitch;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.db2.DevUnionDevice;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.db3.DevDownDataPO;
import com.polycis.main.entity.db3.DevUpDataPO;
import com.polycis.main.entity.vo.QueryTimePO;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db2.IDevUnionDeviceService;
import com.polycis.main.service.db3.IDevDataDownService;
import com.polycis.main.service.db3.IDevDataUpService;
import com.polycis.main.service.db3.IDevDataWarnService;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class IMybatisPlusDB3ServiceImpl implements IMybatisPlusDB3Service {

    @Autowired
    private IDevDataDownService iDevDataDownService;
    @Autowired
    private IDevDataUpService iDevDataUpService;

    @Autowired
    private IDevDataWarnService iDevDataWarnService;

    @Autowired
    private IDevUnionDeviceService iDevUnionDeviceService;

    @Autowired
    private IDeviceService iDeviceService;


    @Override
    public Page<DevDownDataPO> selectSingleDownData(RequestVO requestVO) {


        PageInfoVO pageInfo = requestVO.getPageInfo();
        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);

        Page<DevDownDataPO> devDownDataPOPage = new Page<DevDownDataPO>(pageInfo.getCurrentPage(), pageInfo.getPageSize());

        EntityWrapper<DevDownDataPO> deviceEntityWrapper = new EntityWrapper<DevDownDataPO>();

        deviceEntityWrapper.eq("device_uuid", device.getDeviceUuid());

        Page<DevDownDataPO> devDownDataPOPage1 = iDevDataDownService.selectPage(devDownDataPOPage, deviceEntityWrapper);

        return devDownDataPOPage1;


    }

    @Override
    public Page<DevUpDataPO> selectSingleUpData(RequestVO requestVO) {

        PageInfoVO pageInfo = requestVO.getPageInfo();
        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);

        Page<DevUpDataPO> devUpDataPOPage1 = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());

        EntityWrapper<DevUpDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();

        devUpDataPOEntityWrapper.eq("device_uuid", device.getDeviceUuid());
        devUpDataPOEntityWrapper.orderBy("create_time desc");

        Page<DevUpDataPO> devUpDataPOPage = iDevDataUpService.selectPage(devUpDataPOPage1, devUpDataPOEntityWrapper);

        return devUpDataPOPage;

    }

    @Override
    public Integer selectUpDataCount(List<String> list) {

        EntityWrapper<DevUpDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();
        if (list.size() == 0) {
            devUpDataPOEntityWrapper.eq("device_uuid", "不存在po");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", list);
        }
        devUpDataPOEntityWrapper.in("device_uuid", list);
        devUpDataPOEntityWrapper.addFilter("TO_DAYS(create_time)=TO_DAYS(NOW())");
        return iDevDataUpService.selectCount(devUpDataPOEntityWrapper);
    }

    @Override
    public Integer selectDownDataCount(List<String> list) {
        /*list.forEach(s-> System.out.println("xiafa"+s.toString()));*/

        EntityWrapper<DevDownDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();
        if (list.size() == 0) {
            devUpDataPOEntityWrapper.eq("device_uuid", "不可用po");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", list);
        }
        devUpDataPOEntityWrapper.addFilter("TO_DAYS(create_time)=TO_DAYS(NOW())");
        return iDevDataDownService.selectCount(devUpDataPOEntityWrapper);

    }

    @Override
    public List<Map<String, Object>> selectAWeekData(List<String> list) {
        return iDevDataUpService.selectAWeekData(list);
    }

    @Override
    public Page<DevUpDataPO> selectAppUpData(List<Object> objects, PageInfoVO pageInfo) {

        EntityWrapper<DevUpDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();
        if (objects.size() == 0) {
            // 符合的 uuid不存在,则设置个中文字符,永远查不到
            devUpDataPOEntityWrapper.eq("device_uuid", "不存在polycis");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", objects);
        }

        Page<DevUpDataPO> devUpDataPOPage = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<DevUpDataPO> devUpDataPOPage1 = iDevDataUpService.selectPage(devUpDataPOPage, devUpDataPOEntityWrapper);

        return devUpDataPOPage1;
    }

    @Override
    public Page<DevDownDataPO> selectAppDownData(List<Object> objects, PageInfoVO pageInfo) {

        EntityWrapper<DevDownDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();
        if (objects.size() == 0) {
            // 符合的 uuid不存在,则设置个中文字符,永远查不到
            devUpDataPOEntityWrapper.eq("device_uuid", "不存在polycis");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", objects);
        }

        Page<DevDownDataPO> devUpDataPOPage = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<DevDownDataPO> devUpDataPOPage1 = iDevDataDownService.selectPage(devUpDataPOPage, devUpDataPOEntityWrapper);

        return devUpDataPOPage1;

    }

    @Override
    public Map<String, Object> selectAppUpDownDataCount(List<Object> objects) {

        Map<String, Object> map = new HashMap<>();
        EntityWrapper<DevDownDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();
        if (objects.size() == 0) {
            devUpDataPOEntityWrapper.eq("device_uuid", "不存在po");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", objects);
        }

        int down = iDevDataDownService.selectCount(devUpDataPOEntityWrapper);
        map.put("down", down);

        EntityWrapper<DevUpDataPO> devUpDataPOEntityWrapper1 = new EntityWrapper<>();
        devUpDataPOEntityWrapper1.in("device_uuid", objects);
        int up = iDevDataUpService.selectCount(devUpDataPOEntityWrapper1);
        map.put("up", up);
        return map;

    }

    @Override
    public Page<DevDataWarn> selectPage(List<Object> list, PageInfoVO pageInfo) {
        EntityWrapper<DevDataWarn> devDataWarnEntityWrapper = new EntityWrapper<>();
        if (list.size() == 0) {
            devDataWarnEntityWrapper.eq("device_uuid", "不存在polycis");
        } else {
            devDataWarnEntityWrapper.in("device_uuid", list);
        }


        Page<DevDataWarn> devDataWarnPage = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<DevDataWarn> devDataWarnPage1 = iDevDataWarnService.selectPage(devDataWarnPage, devDataWarnEntityWrapper);

        return devDataWarnPage1;
    }

    @Override
    public Integer selectAppAlarm(List<String> list) {

        EntityWrapper<DevDataWarn> devDataWarnEntityWrapper = new EntityWrapper<>();
        if (list.size() == 0) {
            devDataWarnEntityWrapper.eq("device_uuid", "不存在po");
        } else {
            devDataWarnEntityWrapper.in("device_uuid", list);
        }

        devDataWarnEntityWrapper.eq("status", 0);
        return iDevDataWarnService.selectCount(devDataWarnEntityWrapper);
    }

    @Override
    public Page<DevDownDataPO> selectAppDownData(RequestVO requestVO) {

        PageInfoVO pageInfo = requestVO.getPageInfo();

        QueryTimePO queryTimePO = JSON.parseObject(JSON.toJSONString(requestVO.getData()), QueryTimePO.class);

        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", queryTimePO.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);


        EntityWrapper<DevDownDataPO> devDownDataPOEntityWrapper = new EntityWrapper<>();
        if (objects.size() == 0) {
            devDownDataPOEntityWrapper.eq("device_uuid", "不存在po");
        } else {
            devDownDataPOEntityWrapper.in("device_uuid", objects);
        }


        if (null != queryTimePO.getStartTime() && null != queryTimePO.getEndTime() && !queryTimePO.getEndTime().toString().isEmpty()
                && !queryTimePO.getStartTime().toString().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startTime = formatter.format(queryTimePO.getStartTime());
            String endTime = formatter.format(queryTimePO.getEndTime());
            devDownDataPOEntityWrapper.between("report_time", startTime, endTime);
        }
        Page<DevDownDataPO> page = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<DevDownDataPO> devUpDataPOPage = iDevDataDownService.selectPage(page, devDownDataPOEntityWrapper);

        return devUpDataPOPage;
    }

    @Override
    public Page<DevUpDataPO> selectAppUpData(RequestVO requestVO) {
        PageInfoVO pageInfo = requestVO.getPageInfo();
        QueryTimePO queryTimePO = JSON.parseObject(JSON.toJSONString(requestVO.getData()), QueryTimePO.class);


        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.eq("app_id", queryTimePO.getId());
        deviceEntityWrapper.setSqlSelect("device_uuid");
        List<Object> objects = iDeviceService.selectObjs(deviceEntityWrapper);

        EntityWrapper<DevUpDataPO> devUpDataPOEntityWrapper = new EntityWrapper<>();

        if (objects.size() == 0) {
            devUpDataPOEntityWrapper.eq("device_uuid", "不存在po");
        } else {
            devUpDataPOEntityWrapper.in("device_uuid", objects);
        }

        if (null != queryTimePO.getStartTime() && null != queryTimePO.getEndTime() && !queryTimePO.getEndTime().toString().isEmpty()
                && !queryTimePO.getStartTime().toString().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startTime = formatter.format(queryTimePO.getStartTime());
            String endTime = formatter.format(queryTimePO.getEndTime());
            devUpDataPOEntityWrapper.between("report_time", startTime, endTime);
        }
        Page<DevUpDataPO> page = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<DevUpDataPO> devUpDataPOPage = iDevDataUpService.selectPage(page, devUpDataPOEntityWrapper);

        return devUpDataPOPage;
    }


    @Override
    public Object selectDevWarnCount() {
        EntityWrapper<DevDataWarn> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(distinct device_uuid)")
                .in("status", Stream.of(0, 1).toArray());
        int devicecount = iDevDataWarnService.selectCount(wrapper);
        Object o = iDevDataWarnService.selectObj(wrapper);
        return o;
    }


    @DataSourceSwitch(DBTypeEnum.db2)
    @Override
    public Integer selectDevOnLineCount() {
        // status 1为在线
        int i = iDevUnionDeviceService.selectCount(new EntityWrapper<DevUnionDevice>().eq("status", 1));
        return i;
    }

    @DataSourceSwitch(DBTypeEnum.db1)
    @Override
    public int selectDevCount() {

        return iDeviceService.selectCount(new EntityWrapper<Device>().eq("is_delete", MainConstants.UN_DELETE));
    }

    @Override
    public Integer selectDevWarnCount0() {

        return iDevDataWarnService.selectCount(new EntityWrapper<DevDataWarn>().eq("status", 0));
    }

    @Override
    public Integer selectDevWarnCount1() {
        return iDevDataWarnService.selectCount(new EntityWrapper<DevDataWarn>().eq("status", 1));
    }

    @Override
    public Page<DevDataWarn> selectDevWarnPage(RequestVO requestVO) {
        PageInfoVO pageInfo = requestVO.getPageInfo();

        Page<DevDataWarn> page = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());

        return iDevDataWarnService.selectPage(page, new EntityWrapper<DevDataWarn>().eq("status", 0).setSqlSelect("device_uuid,create_time"));
    }

    @DataSourceSwitch(DBTypeEnum.db1)
    @Override
    public Page<DevDataWarn> selectDevWarnPageName(Page<DevDataWarn> page0) {
        page0.getRecords().forEach(devwarn -> {
                    EntityWrapper<Device> wrapper = new EntityWrapper<>();
                    wrapper.setSqlSelect("name")
                            .eq("device_uuid", devwarn.getDeviceUuid())
                            .eq("is_delete", MainConstants.UN_DELETE)
                            .last("limit 1");
                    Object o = iDeviceService.selectObj(wrapper);
                    devwarn.setMac(o.toString());
                }
        );
        return page0;
    }

    @Override
    public List<Map<String, Object>> selectAWeekdataOss() {



        return iDevDataUpService.selectAWeekdataOss();

    }
}
