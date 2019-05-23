package com.polycis.main.service.db1.impl.lora;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.client.deviceProfile.LoraDevProfileFeignClient;
import com.polycis.main.client.initResource.LoraInitResourceFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.DeviceProfile;
import com.polycis.main.entity.lora.LoraDeviceProfileDTO;
import com.polycis.main.mapper.db1.DeviceProfileMapper;
import com.polycis.main.service.db1.lora.IDeviceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 设备配置文件 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@Service
public class DeviceProfileServiceImpl extends ServiceImpl<DeviceProfileMapper, DeviceProfile> implements IDeviceProfileService {

    @Autowired
    private DeviceProfileMapper deviceProfileMapper;
    @Autowired
    private LoraDevProfileFeignClient loraDevProfileFeignClient;
    @Autowired
    private LoraInitResourceFeignClient loraInitResourceFeignClient;

    /**
     * 设备配置文件
     * @param dpFile
     * @return
     */
    @Override
    public ApiResult add(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        String orgId = this.loraInitResourceFeignClient.getInitOrganizationId().getData();
        String networkId = this.loraInitResourceFeignClient.getInitNetworkId().getData();

        //TODO loraserver中添加server_profile
        LoraDeviceProfileDTO dpFileDTO = new LoraDeviceProfileDTO();
        dpFileDTO.setName(dpFile.getName());
        dpFileDTO.setNetworkServerID(networkId);
        dpFileDTO.setOrganizationID(orgId);
        dpFileDTO.setMacVersion(dpFile.getMacVersion());
        dpFileDTO.setRegParamsRevision(dpFile.getRegParamsRevision());
        dpFileDTO.setFactoryPresetFreqsStr(dpFile.getFactoryPresetFreqsStr());
        dpFileDTO.setMaxEIRP(dpFile.getMaxEIRP());
        dpFileDTO.setSupportsJoin(dpFile.getSupportsJoin());
        dpFileDTO.setRxDelay1(dpFile.getRxDelay1());
        dpFileDTO.setRxDROffset1(dpFile.getRxDROffset1());
        dpFileDTO.setRxDataRate2(dpFile.getRxDataRate2());
        dpFileDTO.setRxFreq2(dpFile.getRxFreq2());
        dpFileDTO.setSupportsClassB(dpFile.getSupportsClassB());
        dpFileDTO.setClassBTimeout(dpFile.getClassBTimeout());
        dpFileDTO.setPingSlotPeriod(dpFile.getPingSlotPeriod());
        dpFileDTO.setPingSlotDR(dpFile.getPingSlotDR());
        dpFileDTO.setPingSlotFreq(dpFile.getPingSlotFreq());
        dpFileDTO.setSupportsClassC(dpFile.getSupportsClassC());
        dpFileDTO.setClassCTimeout(dpFile.getClassCTimeout());
        ApiResult<String> addResult = this.loraDevProfileFeignClient.post(dpFileDTO);
        if(addResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(addResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        dpFile.setCreateTime(date);
        dpFile.setUpdateTime(date);
        dpFile.setNetworkServerID(networkId);
        dpFile.setOrganizationID(orgId);
        dpFile.setDeviceProfileID(addResult.getData());

        this.deviceProfileMapper.insert(dpFile);

        return apiResult;
    }

    /**
     * 查看设备配置列表
     * @param currentPage
     * @param pageSize
     * @param dpFile
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DeviceProfile> findList(Integer currentPage, Integer pageSize, DeviceProfile dpFile) {
        Page<DeviceProfile> page = new Page<>(currentPage, pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("pageNumber", (currentPage - 1) * pageSize);
        param.put("pageSize", pageSize);
        param.put("classType", dpFile.getClassType());
        param.put("name", dpFile.getName());
        if(null != dpFile.getSupportsJoin()){
            param.put("supportsJoin", dpFile.getSupportsJoin() ? 1 : 0);
        }
        List<DeviceProfile> list = this.deviceProfileMapper.findList(param);
        Integer count = this.deviceProfileMapper.findListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }

    /**
     * 更新设备配置文件
     * @param dpFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> updateDeviceProfile(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        DeviceProfile prevDpFile = this.selectById(dpFile.getId());
        if(null == prevDpFile){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO loraserver中更新server_profile
        LoraDeviceProfileDTO dpFileDTO = new LoraDeviceProfileDTO();
        dpFileDTO.setId(prevDpFile.getDeviceProfileID());
        dpFileDTO.setName(dpFile.getName());
        dpFileDTO.setMacVersion(dpFile.getMacVersion());
        dpFileDTO.setRegParamsRevision(dpFile.getRegParamsRevision());
        dpFileDTO.setFactoryPresetFreqsStr(dpFile.getFactoryPresetFreqsStr());
        dpFileDTO.setMaxEIRP(dpFile.getMaxEIRP());
        dpFileDTO.setSupportsJoin(dpFile.getSupportsJoin());
        dpFileDTO.setRxDelay1(dpFile.getRxDelay1());
        dpFileDTO.setRxDROffset1(dpFile.getRxDROffset1());
        dpFileDTO.setRxDataRate2(dpFile.getRxDataRate2());
        dpFileDTO.setRxFreq2(dpFile.getRxFreq2());
        dpFileDTO.setSupportsClassB(dpFile.getSupportsClassB());
        dpFileDTO.setClassBTimeout(dpFile.getClassBTimeout());
        dpFileDTO.setPingSlotPeriod(dpFile.getPingSlotPeriod());
        dpFileDTO.setPingSlotDR(dpFile.getPingSlotDR());
        dpFileDTO.setPingSlotFreq(dpFile.getPingSlotFreq());
        dpFileDTO.setSupportsClassC(dpFile.getSupportsClassC());
        dpFileDTO.setClassCTimeout(dpFile.getClassCTimeout());
        ApiResult<String> addResult = this.loraDevProfileFeignClient.put(dpFileDTO);
        if(addResult.getCode() != CommonCode.SUCCESS.getKey()){
            throw new RuntimeException(addResult.getMsg());
        }

        Date date = Calendar.getInstance().getTime();
        dpFile.setUpdateTime(date);
        this.deviceProfileMapper.updateById(dpFile);

        return apiResult;
    }

    /**
     * 删除设备配置文件
     * @param dpFile
     * @return
     */
    @Transactional
    @Override
    public ApiResult<String> deleteDeviceProfile(DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);

        DeviceProfile prevDpFile = this.selectById(dpFile.getId());
        if(null == prevDpFile){
            throw new RuntimeException("当前记录已删除");
        }

        //TODO loraserver中删除server_profile
        LoraDeviceProfileDTO dpFileDTO = new LoraDeviceProfileDTO();
        dpFileDTO.setId(prevDpFile.getDeviceProfileID());
        ApiResult<String> delResult = this.loraDevProfileFeignClient.delete(dpFileDTO);
        if (delResult.getCode() != CommonCode.SUCCESS.getKey()) {
            throw new RuntimeException(delResult.getMsg());
        }

        this.deviceProfileMapper.deleteById(dpFile);

        return apiResult;
    }

    /**
     * 查找所有的设备配置文件
     * @return List<DeviceProfile>
     */
    @Override
    public ApiResult<List<DeviceProfile>> findListAll() {
        ApiResult<List<DeviceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(this.deviceProfileMapper.findListAll());
        return apiResult;
    }
}
