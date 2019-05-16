package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Device;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.Users;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IDeviceService extends IService<Device> {

    Page<Device> selectAppDeviceList(RequestVO requestVO);

    Page<Device> selectProductDevList(RequestVO requestVO);

    Integer selectOrgCount(Users currentUser);

    Integer selectTodayCount(Users currentUser);

    // 查询组织下的device_uuid
    List<String> selectDeviceList(Integer org);

    //  查询app下的devid
    List<String> selectDevList(Integer appid);

    Page<Device> selectDevicePage(RequestVO requestVO);
}
