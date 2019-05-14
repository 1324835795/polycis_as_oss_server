package com.polycis.main.mapper.db1;

import com.baomidou.mybatisplus.annotations.DataSource;
import com.polycis.main.entity.AppOrgRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用与组织关联表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */

@Mapper
public interface AppOrgRelationMapper extends BaseMapper<AppOrgRelation> {

    @Select(" SELECT ia.app_eui AS eui\n" +
            "        FROM iot_app_org_relation iaor\n" +
            "        LEFT JOIN iot_app ia on ia.id =iaor.app_id AND  ia.is_delete=1\n" +
            "        WHERE iaor.organization_id =#{org}")
    List<String> selectAppEui(Integer org);

    @Select("\n" +
            "SELECT  iaor.app_id\n" +
            "FROM iot_app_org_relation iaor\n" +
            "INNER JOIN iot_app ia ON ia.id =iaor.`app_id` AND ia.is_delete=1\n" +
            "WHERE iaor.organization_id =#{param1}\n" +
            "AND ia.`name` LIKE \"%\"#{param2}\"%\"")
    List<Integer> selecctAppId(Integer org, String description);
}
