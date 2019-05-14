package com.polycis.main.service.db3;

import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.db3.DevUpDataPO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-24
 */
public interface IDevDataUpService extends IService<DevUpDataPO> {

    List<Map<String,Object>> selectAWeekData(List<String> list);
}
