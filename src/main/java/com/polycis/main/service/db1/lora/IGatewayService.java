package com.polycis.main.service.db1.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.Gateway;

/**
 * <p>
 * 网关 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public interface IGatewayService extends IService<Gateway> {

    /**
     * 新增网关
     * @param gw
     * @return
     */
    ApiResult add(Gateway gw);

    /**
     * 查看服务配置列表
     * @param currentPage
     * @param pageSize
     * @param gw
     * @return
     */
    Page<Gateway> findList(Integer currentPage, Integer pageSize, Gateway gw);

    /**
     * 更新网关
     * @param gw
     * @return
     */
    ApiResult<String> updateGateway(Gateway gw);

    /**
     * 删除网关
     * @param gw
     * @return
     */
    ApiResult<String> deleteGateway(Gateway gw);
}
