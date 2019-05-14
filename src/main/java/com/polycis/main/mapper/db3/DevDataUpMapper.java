package com.polycis.main.mapper.db3;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.db3.DevUpDataPO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-24
 */
public interface DevDataUpMapper extends BaseMapper<DevUpDataPO> {

    List<Map<String,Object>> selectAWeekData(List<String> list);
}
