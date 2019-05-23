package com.polycis.main.service.db1.impl.lora;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.client.initResource.LoraInitResourceFeignClient;
import com.polycis.main.client.serviceProfile.LoraServiceProfileFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraServiceProfileDTO;
import com.polycis.main.entity.lora.ServiceProfile;
import com.polycis.main.mapper.db1.ServiceProfileMapper;
import com.polycis.main.service.db1.lora.IServiceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务配置文件 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
@Service
@Transactional
public class ServiceProfileServiceImpl extends ServiceImpl<ServiceProfileMapper, ServiceProfile> implements IServiceProfileService {

    @Autowired
    private ServiceProfileMapper serviceProfileMapper;
    @Autowired
    private LoraServiceProfileFeignClient loraServiceProfileFeignClient;
    @Autowired
    private LoraInitResourceFeignClient loraInitResourceFeignClient;

    /**
     * 服务配置文件
     * @param spFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult add(ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        String orgId = this.loraInitResourceFeignClient.getInitOrganizationId().getData();
        String networkId = this.loraInitResourceFeignClient.getInitNetworkId().getData();

        //TODO loraserver中添加server_profile
        LoraServiceProfileDTO spFileDTO = new LoraServiceProfileDTO();
        spFileDTO.setName(spFile.getName());
        spFileDTO.setNetworkServerID(networkId);
        spFileDTO.setOrganizationID(orgId);
        spFileDTO.setAddGWMetaData(spFile.getAddGWMetaData());
        spFileDTO.setDevStatusReqFreq(spFile.getDevStatusReqFreq());
        spFileDTO.setReportDevStatusBattery(spFile.getReportDevStatusBattery());
        spFileDTO.setReportDevStatusMargin(spFile.getReportDevStatusMargin());
        spFileDTO.setDrMin(spFile.getDrMin());
        spFileDTO.setDrMax(spFile.getDrMax());
        ApiResult<String> addResult = this.loraServiceProfileFeignClient.post(spFileDTO);
        if(addResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(addResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        spFile.setCreateTime(date);
        spFile.setUpdateTime(date);
        spFile.setNetworkServerID(networkId);
        spFile.setOrganizationID(orgId);
        spFile.setServiceProfileId(addResult.getData());
        this.serviceProfileMapper.insert(spFile);

        return apiResult;
    }

    /**
     * 查看服务配置列表
     * @param currentPage
     * @param pageSize
     * @param spFile
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ServiceProfile> findList(Integer currentPage, Integer pageSize, ServiceProfile spFile) {
        Page<ServiceProfile> page = new Page<>(currentPage, pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("pageNumber", (currentPage - 1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("spName", spFile.getName());
        List<ServiceProfile> list = this.serviceProfileMapper.findList(param);
        Integer count = this.serviceProfileMapper.findListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }

    /**
     * 更新服务配置文件
     * @param spFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> updateServiceProfile(ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        ServiceProfile prevSpFile = this.selectById(spFile.getId());
        if(null == prevSpFile){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO loraserver中更新server_profile
        LoraServiceProfileDTO spFileDTO = new LoraServiceProfileDTO();
        spFileDTO.setId(prevSpFile.getServiceProfileId());
        spFileDTO.setName(spFile.getName());
        spFileDTO.setAddGWMetaData(spFile.getAddGWMetaData());
        spFileDTO.setDevStatusReqFreq(spFile.getDevStatusReqFreq());
        spFileDTO.setReportDevStatusBattery(spFile.getReportDevStatusBattery());
        spFileDTO.setReportDevStatusMargin(spFile.getReportDevStatusMargin());
        spFileDTO.setDrMin(spFile.getDrMin());
        spFileDTO.setDrMax(spFile.getDrMax());
        ApiResult<String> addResult = this.loraServiceProfileFeignClient.put(spFileDTO);
        if(addResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(addResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        spFile.setUpdateTime(date);
        this.serviceProfileMapper.updateById(spFile);

        return apiResult;
    }

    /**
     * 删除服务配置文件
     * @param spFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> deleteServiceProfile(ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        ServiceProfile prevSpFile = this.selectById(spFile.getId());
        if(null == prevSpFile){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO loraserver中删除server_profile
        LoraServiceProfileDTO spFileDTO = new LoraServiceProfileDTO();
        spFileDTO.setId(prevSpFile.getServiceProfileId());
        ApiResult<String> delResult = this.loraServiceProfileFeignClient.delete(spFileDTO);
        if(delResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(delResult.getMsg());
        }

        this.serviceProfileMapper.deleteById(spFile.getId());

        return apiResult;
    }

    /**
     * 查看全部服务配置列表
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ApiResult<List<ServiceProfile>> findListAll() {
        ApiResult<List<ServiceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(this.serviceProfileMapper.findListAll());
        return apiResult;
    }


}
