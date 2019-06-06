package com.polycis.main.service.db1.impl.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.client.gateway.LoraGatewayFeignClient;
import com.polycis.main.client.initResource.LoraInitResourceFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.GatewayPro;
import com.polycis.main.entity.lora.Gateway;
import com.polycis.main.entity.lora.LoraGatewayDTO;
import com.polycis.main.mapper.db1.GatewayMapper;
import com.polycis.main.service.db1.IGatewayProService;
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
@Transactional
@Service
public class GatewayServiceImpl extends ServiceImpl<GatewayMapper, Gateway> implements IGatewayService {

    @Autowired
    private GatewayMapper gatewayMapper;
    @Autowired
    private LoraGatewayFeignClient loraGatewayFeignClient;
    @Autowired
    private LoraInitResourceFeignClient loraInitResourceFeignClient;
    @Autowired
    private IGatewayProService gatewayProService;

    /**
     * 服务配置文件
     * @param gw
     * @return
     */
    @Transactional
    @Override
    public ApiResult add(Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        LoraGatewayDTO tempDTO = new LoraGatewayDTO();
        tempDTO.setId(gw.getMac());
        ApiResult<LoraGatewayDTO> sch_dto = this.loraGatewayFeignClient.get(tempDTO);
        if(sch_dto.getCode() == CommonCode.SUCCESS.getKey() && null != sch_dto.getData()){
            throw new RuntimeException("当前MAC地址已存在");
        }

        String orgId = this.loraInitResourceFeignClient.getInitOrganizationId().getData();
        String networkId = this.loraInitResourceFeignClient.getInitNetworkId().getData();

        //TODO 要关联接入平台的loraServer
        LoraGatewayDTO loraGatewayDTO = new LoraGatewayDTO();
        loraGatewayDTO.setId(gw.getMac());
        loraGatewayDTO.setName(gw.getName());
        loraGatewayDTO.setDescription(gw.getDescription());
        loraGatewayDTO.setLatitude(gw.getLatitude());
        loraGatewayDTO.setLongitude(gw.getLongitude());
        loraGatewayDTO.setAltitude(null == gw.getHightUp() ? "0" : gw.getHightUp().toString());
        loraGatewayDTO.setOrganizationID(orgId);
        loraGatewayDTO.setNetworkServerID(networkId);
        loraGatewayDTO.setDiscoveryEnabled(gw.getDiscoveryEnabled());
        GatewayPro gpFile = this.gatewayProService.selectById(gw.getGatewayProfileID());
        loraGatewayDTO.setGatewayProfileID(gpFile.getGatewayProfileId());
        ApiResult<String> loraGwResult = this.loraGatewayFeignClient.post(loraGatewayDTO);
        if(loraGwResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(loraGwResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        gw.setCreateTime(date);
        gw.setUpdateTime(date);
        gw.setNetworkServerID(networkId);
        gw.setOrganizationID(orgId);
        //TODO loraserver中添加server_profile
        gw.setGatewayId(loraGwResult.getData());
        this.gatewayMapper.insert(gw);

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
        param.put("gwOrgId", gw.getId());
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

        Gateway prevGw = this.selectById(gw.getId());
        if(null == prevGw){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO 要关联接入平台的loraServer
        LoraGatewayDTO loraGatewayDTO = new LoraGatewayDTO();
        loraGatewayDTO.setId(prevGw.getMac());
        loraGatewayDTO.setName(gw.getName());
        loraGatewayDTO.setDescription(gw.getDescription());
        loraGatewayDTO.setLatitude(gw.getLatitude());
        loraGatewayDTO.setLongitude(gw.getLongitude());
        loraGatewayDTO.setAltitude(null == gw.getHightUp() ? "0" : gw.getHightUp().toString());
        loraGatewayDTO.setDiscoveryEnabled(gw.getDiscoveryEnabled());
        GatewayPro gpFile = this.gatewayProService.selectById(gw.getGatewayProfileID());
        loraGatewayDTO.setGatewayProfileID(gpFile.getGatewayProfileId());
        ApiResult<String> loraGwResult = this.loraGatewayFeignClient.put(loraGatewayDTO);
        if(loraGwResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(loraGwResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        gw.setUpdateTime(date);
        this.gatewayMapper.updateById(gw);

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

        Gateway prevGw = this.selectById(gw.getId());
        if(null == prevGw){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO loraserver中删除server_profile
        LoraGatewayDTO dto = new LoraGatewayDTO();
        dto.setId(prevGw.getMac());
        ApiResult<String> delResult = this.loraGatewayFeignClient.delete(dto);
        if(delResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(delResult.getMsg());
        }

        this.gatewayMapper.deleteById(gw);
        return apiResult;
    }

}
