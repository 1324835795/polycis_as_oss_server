package com.polycis.main.service.db1.impl.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.Gateway;
import com.polycis.main.mapper.db1.GatewayMapper;
import com.polycis.main.service.db1.lora.IGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 网关 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@Service
public class GatewayServiceImpl extends ServiceImpl<GatewayMapper, Gateway> implements IGatewayService {

    @Autowired
    private GatewayMapper gatewayMapper;

    /**
     * 服务配置文件
     * @param gw
     * @return
     */
    @Override
    public ApiResult add(Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Date date = Calendar.getInstance().getTime();
        gw.setCreateTime(date);
        gw.setUpdateTime(date);

        //TODO 要关联接入平台
        gw.setNetworkServerID("1");
        gw.setOrganizationID("1");

        this.gatewayMapper.insert(gw);

        //TODO loraserver中添加server_profile
//        this.gatewayMapper.updateById(gw);
        return apiResult;
    }

    /**
     * 查看服务配置列表
     * @param currentPage
     * @param pageSize
     * @param gw
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<Gateway> findList(Integer currentPage, Integer pageSize, Gateway gw) {
        Page<Gateway> page = new Page<>(currentPage, pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("pageNumber", (currentPage - 1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("gwName", gw.getName());
        param.put("gwMac", gw.getName());
        List<Gateway> list = this.gatewayMapper.findList(param);
        Integer count = this.gatewayMapper.findListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }

    /**
     * 更新服务配置文件
     * @param gw
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> updateGateway(Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Date date = Calendar.getInstance().getTime();
        gw.setUpdateTime(date);
        this.gatewayMapper.updateById(gw);

        //TODO loraserver中更新server_profile
//        this.gatewayMapper.updateById(gw);
        return apiResult;
    }

    /**
     * 删除服务配置文件
     * @param gw
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> deleteGateway(Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        this.gatewayMapper.deleteById(gw);

        //TODO loraserver中删除server_profile
//        this.gatewayMapper.updateById(gw);
        return apiResult;
    }

}
