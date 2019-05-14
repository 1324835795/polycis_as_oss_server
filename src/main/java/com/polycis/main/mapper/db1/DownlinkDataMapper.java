package com.polycis.main.mapper.db1;

import com.polycis.main.entity.DownlinkData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 设备下行数据表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Mapper
public interface DownlinkDataMapper extends BaseMapper<DownlinkData> {

    @Select("  SELECT COUNT(id)\n" +
            "        FROM iot_downlink_data\n" +
            "        WHERE TO_DAYS(create_time)=TO_DAYS(NOW())")
    Integer selectTodayCount(Integer org);



    @Select("SELECT * FROM iot_downlink_data idd \n" +
            "LEFT JOIN iot_device d ON d.`device_uuid`=idd.`device_id`\n" +
            "WHERE d.`product_id` = #{param1}\n" +
            "ORDER BY idd.`create_time` DESC\n" +
            "LIMIT #{param2},#{param3}")
    List<DownlinkData> selectDownData(Integer org, Integer page, Integer size);



    @Select("SELECT count(*) FROM iot_downlink_data idd \n" +
            "LEFT JOIN iot_device d ON d.`device_uuid`=idd.`device_id`\n" +
            "WHERE d.`product_id` = #{param1}")
    Integer selectDownDataCount(Integer org);
}
