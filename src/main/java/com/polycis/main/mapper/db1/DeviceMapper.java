package com.polycis.main.mapper.db1;

import com.polycis.main.common.datasource.DataSourceSwitch;
import com.polycis.main.entity.Device;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    @Select(" SELECT  COUNT(id.id)\n" +
            "        FROM iot_app ia\n" +
            "        LEFT JOIN iot_device id ON ia.id=id.app_id AND  id.is_delete=1\n" +
            "        AND ia.id  IN (SELECT app_id FROM iot_app_org_relation iaor WHERE iaor.organization_id=#{org})\n" +
            "        AND  ia.is_delete=1")
    Integer selectOrgCount(Integer org);

    /*@Select("SELECT  COUNT(id.id)\n" +
            "        FROM iot_app ia\n" +
            "        LEFT JOIN iot_device id ON ia.id=id.app_id AND  id.is_delete=1\n" +
            "        AND ia.id  IN (SELECT app_id FROM iot_app_org_relation iaor WHERE iaor.organization_id=#{org})\n" +
            "        AND  ia.is_delete=1\n" +
            "        AND TO_DAYS(id.create_time) =TO_DAYS(NOW())")*/
    @Select("  SELECT count(id.id)\n" +
            "                   from iot_device id\n" +
            "                   inner join iot_app ia ON ia.id=id.app_id AND  id.is_delete=1 and ia.is_delete=1\n" +
            "                   AND ia.id  IN (SELECT app_id FROM iot_app_org_relation iaor WHERE iaor.organization_id=#{org})\n" +
            "                   where TO_DAYS(id.create_time) =TO_DAYS(NOW())")
    Integer selectTodayCount(Integer org);

   /* @Select("SELECT id.device_uuid\n" +
            "        FROM iot_app_org_relation iaor\n" +
            "        LEFT JOIN iot_device id on id.app_id =iaor.app_id\n" +
            "        AND id.is_delete=1")*/
    List<String> selectDevList(Integer appId);

    Integer selectDevCountBypro(Integer productId);

    @DataSourceSwitch()
    @Select("SELECT id.device_uuid\n" +
            "        FROM iot_app_org_relation iaor\n" +
            "        INNER JOIN iot_device id ON id.app_id =iaor.app_id\n" +
            "        AND id.is_delete=1\n" +
            "        WHERE iaor.organization_id=#{org}")
    List<String> selectDeviceList(Integer org);

    List<Device> selectDevicePage(Map<String, Object> param);

    Integer selectAppListCount(Map<String, Object> param);

    List<String> selectOrgDevList(Integer id);
}
