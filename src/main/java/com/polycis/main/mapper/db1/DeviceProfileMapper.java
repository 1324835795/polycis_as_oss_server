package com.polycis.main.mapper.db1;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.lora.DeviceProfile;
import com.polycis.main.entity.lora.DeviceProfile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备配置文件 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public interface DeviceProfileMapper extends BaseMapper<DeviceProfile> {

    /**
     * 查看设备配置文件列表
     * @param param
     * @return
     */
    List<DeviceProfile> findList(Map<String, Object> param);

    /**
     * 查看全部设备配置文件列表
     * @return
     */
    List<DeviceProfile> findListAll();

    /**
     * 查看设备配置文件列表 总数
     * @param param
     * @return
     */
    Integer findListCount(Map<String, Object> param);
}
