package com.polycis.main.mapper.db1;

import com.baomidou.mybatisplus.annotations.DataSource;
import com.polycis.main.common.datasource.DBTypeEnum;
import com.polycis.main.common.datasource.DataSourceSwitch;
import com.polycis.main.entity.App;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.vo.AppVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

import static com.polycis.main.common.datasource.DBTypeEnum.db1;

/**
 * <p>
 * 应用表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Mapper
public interface AppMapper extends BaseMapper<App> {

    @Select("  SELECT \n" +
            "      ip.name,\n" +
            "      COUNT(id.id) AS count\n" +
            "      FROM iot_app ia\n" +
            "      INNER JOIN iot_device id ON id.app_id =ia.id AND id.is_delete =1\n" +
            "      INNER JOIN iot_product ip ON ip.id =id.product_id AND ip.is_delete =1\n" +
            "      WHERE  ia.id=#{id}\n" +
            "      GROUP  BY  ip.id\n" +
            "      ")
    List<Map<String,Object>> selectProduct(Integer id);


    List<String> selectTest();

    List<AppVo> seletMonitorApp(Integer org);

    Integer selectDevIsmine(Integer id, Integer org);
}
