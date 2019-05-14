package com.polycis.main.service.db3;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.entity.db3.DevDownDataPO;
import com.polycis.main.entity.db3.DevUpDataPO;

import java.util.List;
import java.util.Map;

/**
 *  db2接口,英文类名解释的很详细的
 */
public interface IMybatisPlusDB3Service {

    Page<DevDownDataPO> selectSingleDownData(RequestVO requestVO);

    Page<DevUpDataPO> selectSingleUpData(RequestVO requestVO);

    Integer selectUpDataCount(List<String> list);

    Integer selectDownDataCount(List<String> list2);

    List<Map<String,Object>> selectAWeekData(List<String> list);

    Page<DevUpDataPO> selectAppUpData(List<Object> objects, PageInfoVO pageInfo);

    Page<DevDownDataPO> selectAppDownData(List<Object> objects, PageInfoVO pageInfo);

    Map<String,Object> selectAppUpDownDataCount(List<Object> objects);

    Page<DevDataWarn> selectPage(List<Object> list, PageInfoVO pageInfo);

    Integer selectAppAlarm(List<String> strings);
}
