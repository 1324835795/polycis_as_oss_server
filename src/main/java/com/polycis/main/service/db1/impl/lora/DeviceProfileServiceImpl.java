package com.polycis.main.service.db1.impl.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.DeviceProfile;
import com.polycis.main.entity.lora.DeviceProfile;
import com.polycis.main.mapper.db1.DeviceProfileMapper;
import com.polycis.main.mapper.db1.DeviceProfileMapper;
import com.polycis.main.service.db1.lora.IDeviceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 设备配置文件 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@Service
public class DeviceProfileServiceImpl extends ServiceImpl<DeviceProfileMapper, DeviceProfile> implements IDeviceProfileService {

    @Autowired
    private DeviceProfileMapper deviceProfileMapper;

    /**
     * 设备配置文件
     * @param dpFile
     * @return
     */
    @Override
    public ApiResult add(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Date date = Calendar.getInstance().getTime();
        dpFile.setCreateTime(date);
        dpFile.setUpdateTime(date);

        //TODO 要关联接入平台
        dpFile.setNetworkServerID("1");
        dpFile.setOrganizationID("1");

        this.deviceProfileMapper.insert(dpFile);

        //TODO loraserver中添加server_profile
//        this.deviceProfileMapper.updateById(dpFile);
        return apiResult;
    }

    /**
     * 查看设备配置列表
     * @param currentPage
     * @param pageSize
     * @param dpFile
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DeviceProfile> findList(Integer currentPage, Integer pageSize, DeviceProfile dpFile) {
        Page<DeviceProfile> page = new Page<>(currentPage, pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("pageNumber", (currentPage - 1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("classType", dpFile.getClassType());
        param.put("name", dpFile.getName());
        param.put("supportsJoin", dpFile.getSupportsJoin() ? 1 : 0);
        List<DeviceProfile> list = this.deviceProfileMapper.findList(param);
        Integer count = this.deviceProfileMapper.findListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }

    /**
     * 更新设备配置文件
     * @param dpFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> updateDeviceProfile(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Date date = Calendar.getInstance().getTime();
        dpFile.setUpdateTime(date);
        this.deviceProfileMapper.updateById(dpFile);

        //TODO loraserver中更新server_profile
//        this.deviceProfileMapper.updateById(dpFile);
        return apiResult;
    }

    /**
     * 删除设备配置文件
     * @param dpFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> deleteDeviceProfile(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        this.deviceProfileMapper.deleteById(dpFile);

        //TODO loraserver中删除server_profile
//        this.deviceProfileMapper.updateById(dpFile);
        return apiResult;
    }

    /**
     * 查找所有的设备配置文件
     * @return List<DeviceProfile>
     */
    @Override
    public ApiResult<List<DeviceProfile>> findListAll() {
        ApiResult<List<DeviceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(this.deviceProfileMapper.findListAll());
        return apiResult;
    }
}
