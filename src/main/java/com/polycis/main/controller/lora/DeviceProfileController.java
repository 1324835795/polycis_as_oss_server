package com.polycis.main.controller.lora;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.lora.DeviceProfile;
import com.polycis.main.service.db1.lora.IDeviceProfileService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备配置文件 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/lora/deviceProfile")
public class DeviceProfileController {

    protected static Logger LOG = LoggerFactory.getLogger(DeviceProfileController.class);

    @Autowired
    private IDeviceProfileService deviceProfileService;


    @ApiOperation(value = "查询设备配置文件", notes = "查询设备配置文件接口")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult getDeviceProfile(@RequestBody DeviceProfile dpFile) {
        ApiResult<DeviceProfile> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            DeviceProfile dp = this.deviceProfileService.selectById(dpFile);
            apiResult.setData(dp);
        }catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("查询设备配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    @ApiOperation(value = "添加设备配置文件", notes = "添加设备配置文件接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult addDeviceProfile(@RequestBody DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            apiResult = this.deviceProfileService.add(dpFile);
        }catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("新增设备配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


    @ApiOperation(value = "查看设备配置文件列表", notes = "查看设备配置文件列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ApiResult list(@RequestBody RequestVO requestVO) {
        ApiResult<Page<DeviceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Map<String, Object> data = requestVO.getData();
        DeviceProfile dpFile = JSON.parseObject(JSON.toJSONString(data), DeviceProfile.class);
        Page<DeviceProfile> page = this.deviceProfileService.findList(currentPage, pageSize, dpFile);
        apiResult.setData(page);
        return apiResult;
    }
    @ApiOperation(value = "查看全部设备配置文件列表", notes = "查看全部设备配置文件列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public ApiResult listAll(@RequestBody RequestVO requestVO) {
        ApiResult<List<DeviceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult = this.deviceProfileService.findListAll();
        return apiResult;
    }



    @ApiOperation(value = "更新设备配置文件", notes = "更新设备配置文件")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult update(@RequestBody DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            apiResult = this.deviceProfileService.updateDeviceProfile(dpFile);
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("更新设备配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "删除设备配置文件", notes = "删除设备配置文件")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody DeviceProfile dpFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            apiResult = this.deviceProfileService.deleteDeviceProfile(dpFile);
        } catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("删除设备配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

}

