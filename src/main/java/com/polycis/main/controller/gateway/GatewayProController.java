package com.polycis.main.controller.gateway;


import com.alibaba.fastjson.JSON;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.GatewayProChannel;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.vo.GatewayProVO;
import com.polycis.main.service.db1.IGatewayProChannelService;
import com.polycis.main.service.db1.IGatewayProService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/gatewayPro")
public class GatewayProController {


    protected static Logger Log = LoggerFactory.getLogger(GatewayProController.class);

    @Autowired
    IGatewayProService iGatewayProService;
    @Autowired
    IGatewayProChannelService iGatewayProChannelService;
      /**
     * 对外增加网关配置接口
     * @param requestVO
     * @return
     */

    @ApiOperation(value = "新增网关配置", notes = "新增网关配置接口")
    @PostMapping(value = "/addGatePro")
    public ApiResult addGate(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        if (currentUser.getRole().contains(MainConstants.SYS)){

            Integer userId=(Integer)requestVO.getData().get("orgId");
            Map<String, Object> params = (Map<String, Object>) requestVO.getData().get("gatewayProfile");
            GatewayProVO gatewayPro = JSON.parseObject(JSON.toJSONString(params), GatewayProVO.class);
            List<GatewayProChannel> params2 = (List<GatewayProChannel>)((Map<String, Object>) requestVO.getData().get("gatewayProfile")).get("extraChannels");
            gatewayPro.setExtraChannels(params2);
            Boolean b = iGatewayProService.addGatewayPro(userId, gatewayPro);
            if(b){
                return apiResult;
            }
            apiResult.setMsg("配置文件新增失败");
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;

        }

        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;

    }

}

