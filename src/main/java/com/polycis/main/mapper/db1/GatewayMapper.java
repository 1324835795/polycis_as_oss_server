package com.polycis.main.mapper.db1;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.lora.Gateway;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网关 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@Mapper
public interface GatewayMapper extends BaseMapper<Gateway> {

    /**
     * 查看网关列表
     * @param param
     * @return
     */
    List<Gateway> findList(Map<String, Object> param);

    /**
     * 查看网关列表 总数
     * @param param
     * @return
     */
    Integer findListCount(Map<String, Object> param);
}
