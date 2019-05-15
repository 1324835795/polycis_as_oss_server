package com.polycis.main.mapper.db3;

import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.db3.DevDataWarn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-25
 */
@Mapper
public interface DevDataWarnMapper extends BaseMapper<DevDataWarn> {

    List<Map<String,Object>> selectWarnInfo(RequestVO param);

    int selectWarnInfoCount(RequestVO param);

    int updateWarnRead(Map map);
}
