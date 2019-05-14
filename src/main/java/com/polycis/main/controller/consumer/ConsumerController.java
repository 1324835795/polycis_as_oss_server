package com.polycis.main.controller.consumer;


import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.Org;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IOrgService;
import com.polycis.main.service.db1.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/consumer")
@Api(value = "ConsumerController", description = "系统模块:客户信息")
public class ConsumerController {
    protected static Logger LOG = LoggerFactory.getLogger(ConsumerController.class);


    @Autowired
    private IOrgService iOrgService;

    @Autowired
    private RedisFeignClient redisFeignClient;

    @ApiOperation(value = "添加客户", notes = "添加客户")
    @PostMapping("/add")
    public ApiResult add(@RequestBody Org orgusers) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 添加的客户只是超级管理客户,不能添加普通客户,需要接入平台自主维护
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                boolean b = iOrgService.insert(orgusers);
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("客户登录名重复");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

}
