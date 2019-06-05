package com.polycis.main.controller.callname;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/callname")
public class CallNameController {


    @Autowired
    private IMybatisPlusDB3Service mybatisPlusDB3Service;

    @ApiOperation(value = "点名入库接口", notes = "点名入库接口")
    @PostMapping("/receive")
    public ApiResult active(@RequestBody DemoCallName demoCallName) {

        ApiResult apiResult = new ApiResult<>();
        mybatisPlusDB3Service.receiveCallNameData(demoCallName);


        return apiResult;
    }
}
