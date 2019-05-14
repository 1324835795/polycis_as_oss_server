package com.polycis.main.service.db3.impl;

import com.polycis.main.entity.db3.DevUpDataPO;
import com.polycis.main.mapper.db3.DevDataUpMapper;
import com.polycis.main.service.db3.IDevDataUpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-24
 */
@Service
public class DevDataUpServiceImpl extends ServiceImpl<DevDataUpMapper, DevUpDataPO> implements IDevDataUpService {

    @Override
    public List<Map<String, Object>> selectAWeekData(List<String> list) {

       return  baseMapper.selectAWeekData(list);


    }
}
