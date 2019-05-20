package com.polycis.main.controller.device;


import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.service.db1.IIotNetworkDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/networkData")
public class IotNetworkDataController {

    @Autowired
    private IIotNetworkDataService iIotNetworkDataService;

    @ApiOperation(value = "设备数据帧接口", notes = "设备数据帧接口接口")
    @PostMapping("/devNetworkdata")
    public ApiResult DEvNetworkData(@RequestBody RequestVO requestVO) {
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            Integer currentPage = requestVO.getPageInfo().getCurrentPage();
            Integer pageSize = requestVO.getPageInfo().getPageSize();
            Map<String, Object> data = requestVO.getData();
            String deveui = (String) data.get("eui");
            Page aa = iIotNetworkDataService.likeDevData(currentPage, pageSize, deveui);
            apiResult.setData(aa);
        }catch (Exception e){
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg("服务不可用");
        }
        return apiResult;
    }


}

