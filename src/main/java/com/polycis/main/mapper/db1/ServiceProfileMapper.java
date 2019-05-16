package com.polycis.main.mapper.db1;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.lora.ServiceProfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务配置文件 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
@Mapper
public interface ServiceProfileMapper extends BaseMapper<ServiceProfile> {

    /**
     * 查看服务配置列表
     * @param param
     * @return
     */
    List<ServiceProfile> findList(Map<String, Object> param);

    /**
     * 查看服务配置列表 总数
     * @param param
     * @return
     */
    Integer findListCount(Map<String, Object> param);

    /**
     * 查看全部服务配置列表
     * @return
     */
    List<ServiceProfile> findListAll();
}
