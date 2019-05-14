package com.polycis.main.mapper.db2;

import com.polycis.main.entity.db2.DevUnionDevice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.vo.AppVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-22
 */
@Mapper
public interface DevUnionDeviceMapper extends BaseMapper<DevUnionDevice> {

    @Select("SELECT COUNT(id) as count, (CASE WHEN STATUS = '1' THEN '在线' ELSE '不在线' END) as status\n" +
            "        FROM dev_union_device\n" +
            "        WHERE app_eui=#{appEui}\n" +
            "        GROUP BY STATUS;")
    List<Map<String,Integer>> selectDevonline(String appEui);

    AppVo selectAppVo(String appEui);
}
