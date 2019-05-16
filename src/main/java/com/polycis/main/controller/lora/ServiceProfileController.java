package com.polycis.main.controller.lora;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.lora.ServiceProfile;
import com.polycis.main.service.db1.lora.IServiceProfileService;
import io.swagger.annotations.ApiOperation;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
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

/*
 * <p>
 * 服务配置文件 前端控制器
 * </p>
 * @author cheng
 * @since 2019-04-19
*/
@RestController
@RequestMapping("/lora/serviceProfile")
public class ServiceProfileController {
    protected static Logger LOG = LoggerFactory.getLogger(ServiceProfileController.class);

    @Autowired
    private IServiceProfileService serviceProfileService;


    @ApiOperation(value = "添加服务配置文件", notes = "添加服务配置文件接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult addServiceProfile(@RequestBody ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            apiResult = this.serviceProfileService.add(spFile);
        }catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("新增服务配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


    @ApiOperation(value = "查看服务配置文件列表", notes = "查看服务配置文件列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ApiResult list(@RequestBody RequestVO requestVO) {
        ApiResult<Page<ServiceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Map<String, Object> data = requestVO.getData();
        ServiceProfile spFile = JSON.parseObject(JSON.toJSONString(data), ServiceProfile.class);
        Page<ServiceProfile> page = this.serviceProfileService.findList(currentPage, pageSize, spFile);
        apiResult.setData(page);
        return apiResult;
    }

    @ApiOperation(value = "查看全部服务配置文件列表", notes = "查看全部服务配置文件列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public ApiResult listAll(@RequestBody RequestVO requestVO) {
        ApiResult<List<ServiceProfile>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        List<ServiceProfile> list = this.serviceProfileService.findListAll();
        apiResult.setData(list);
        return apiResult;
    }



    @ApiOperation(value = "更新服务配置文件", notes = "更新服务配置文件")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult update(@RequestBody ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            apiResult = this.serviceProfileService.updateServiceProfile(spFile);
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("更新服务配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "删除服务配置文件", notes = "删除服务配置文件")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody ServiceProfile spFile) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            apiResult = this.serviceProfileService.deleteServiceProfile(spFile);
        } catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            LOG.error(String.format("删除服务配置文件异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


}

