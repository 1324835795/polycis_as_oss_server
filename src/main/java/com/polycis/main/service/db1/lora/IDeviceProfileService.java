package com.polycis.main.service.db1.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.DeviceProfile;

import java.util.List;

/**
 * <p>
 * 设备配置文件 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public interface IDeviceProfileService extends IService<DeviceProfile> {

    /**
     * 新增网关
     * @param dpFile
     * @return
     */
    ApiResult add(DeviceProfile dpFile);

    /**
     * 查看服务配置列表
     * @param currentPage
     * @param pageSize
     * @param dpFile
     * @return
     */
    Page<DeviceProfile> findList(Integer currentPage, Integer pageSize, DeviceProfile dpFile);

    /**
     * 查找所有的设备配置文件
     * @return
     */
    ApiResult<List<DeviceProfile>> findListAll();

    /**
     * 更新网关
     * @param dpFile
     * @return
     */
    ApiResult<String> updateDeviceProfile(DeviceProfile dpFile);

    /**
     * 删除网关
     * @param dpFile
     * @return
     */
    ApiResult<String> deleteDeviceProfile(DeviceProfile dpFile);

}
