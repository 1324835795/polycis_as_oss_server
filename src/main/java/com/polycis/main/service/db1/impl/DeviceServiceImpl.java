package com.polycis.main.service.db1.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.Users;
import com.polycis.main.mapper.db1.DeviceMapper;
import com.polycis.main.service.db1.IDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author 乔恺
 * @since 2019-04-19
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private DeviceMapper deviceMapper;


    @Override
    public Page<Device> selectAppDeviceList(RequestVO requestVO) {

        //应该用user来过滤appid是否来源于该user,先不做
        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);

        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("app_id", device.getAppId());
        wrapper.eq("is_delete", 1);
        if (null != device.getDescription() && !"".equals(device.getDescription())) {
            wrapper.andNew().like("name", device.getDescription())
                    .or().like("device_uuid", device.getDescription());
        }
        wrapper.orderBy("create_time desc");
        Page<Device> page = new Page<Device>(requestVO.getPageInfo().getCurrentPage(), requestVO.getPageInfo().getPageSize());
        Page<Device> devicePage = iDeviceService.selectPage(page, wrapper);

        return devicePage;

    }

    @Override
    public Page<Device> selectProductDevList(RequestVO requestVO) {
        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);

        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("product_id", device.getProductId());
        wrapper.eq("is_delete", MainConstants.UN_DELETE);
        if (null != device.getDescription() && !"".equals(device.getDescription())) {
            wrapper.andNew().like("device_uuid", device.getDescription())
                    .or().like("name", device.getDescription());
        }

        /*if(null != device.getStatus() && !"".equals(device.getStatus())) {
            wrapper.eq("status",device.getStatus());
        }*/
        wrapper.orderBy("create_time desc");
        Page<Device> page = new Page<Device>(requestVO.getPageInfo().getCurrentPage(), requestVO.getPageInfo().getPageSize());
        Page<Device> devicePage = iDeviceService.selectPage(page, wrapper);
        return devicePage;
    }

    @Override
    public Integer selectOrgCount(Users currentUser) {

        return deviceMapper.selectOrgCount(currentUser.getOrg());


    }

    @Override
    public Integer selectTodayCount(Users currentUser) {


        return deviceMapper.selectTodayCount(currentUser.getOrg());
    }

    @Override
    public List<String> selectDeviceList(Integer org) {
        return deviceMapper.selectDeviceList(org);

    }

    @Override
    public List<String> selectDevList(Integer id) {
        return deviceMapper.selectDevList(id);
    }

    @Override
    public Page<Device> selectDevicePage(RequestVO requestVO) {
        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);

        Map<String, Object> param = new HashMap<String, Object>();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Page<Device> page = new Page<Device>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        param.put("pageNumber", (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize());

        param.put("pageSize", pageInfo.getPageSize());

        if (null != device.getDescription() && !"".equals(device.getDescription())) {
            param.put("query", device.getDescription());
        } else {
            param.put("query", null);
        }

        if (null != device.getId() && !"".equals(device.getId())) {
            param.put("orgId", device.getId());
        } else {
            param.put("orgId", null);
        }
        List<Device> list = deviceMapper.selectDevicePage(param);
        Integer count = deviceMapper.selectAppListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;

    }

}
