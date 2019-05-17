package com.polycis.main.service.db1.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.ServiceProfile;

import java.util.List;

/**
 * <p>
 * 服务配置文件 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
public interface IServiceProfileService extends IService<ServiceProfile> {

    /**
     * 新增服务配置文件
     * @param spFile
     * @return
     */
    ApiResult add(ServiceProfile spFile);

    /**
     * 查看服务配置列表
     * @param currentPage
     * @param pageSize
     * @param spFile
     * @return
     */
    Page<ServiceProfile> findList(Integer currentPage, Integer pageSize, ServiceProfile spFile);

    /**
     * 更新服务配置文件
     * @param spFile
     * @return
     */
    ApiResult<String> updateServiceProfile(ServiceProfile spFile);

    /**
     * 删除服务配置文件
     * @param spFile
     * @return
     */
    ApiResult<String> deleteServiceProfile(ServiceProfile spFile);


    /**
     * 查看全部服务配置列表
     * @return
     */
    ApiResult<List<ServiceProfile>> findListAll();
}
