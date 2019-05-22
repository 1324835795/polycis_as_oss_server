package com.polycis.main.controller.gateway;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.gatewayProfile.LoraGatewayProfileFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.log.MyLog;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.GatewayPro;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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
     *
     * @param requestVO
     * @return
     */

    @MyLog(describe = "新增网关配置")
    @ApiOperation(value = "新增网关配置", notes = "新增网关配置接口")
    @PostMapping(value = "/addGatePro")
    public ApiResult addGate(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        if (currentUser.getRole().contains(MainConstants.SYS)) {

            Integer userId = (Integer) requestVO.getData().get("orgId");
            Map<String, Object> params = (Map<String, Object>) requestVO.getData().get("gatewayProfile");
            GatewayProVO gatewayPro = JSON.parseObject(JSON.toJSONString(params), GatewayProVO.class);
            List<GatewayProChannel> params2 = (List<GatewayProChannel>) ((Map<String, Object>) requestVO.getData().get("gatewayProfile")).get("extraChannels");
            gatewayPro.setExtraChannels(params2);

            Boolean b = iGatewayProService.addGatewayPro(userId, gatewayPro);
            if (b) {
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


    /**
     * 查询网关配置文件下拉框
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询网关配置文件下拉框", notes = "查询网关配置文件下拉框")
    @PostMapping(value = "/gateProList")
    public ApiResult gatewayList(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Integer network = (Integer) requestVO.getData().get("networkId");
        Integer orgId = (Integer) requestVO.getData().get("orgId");
        //根据network查询
        Map<String, Object> pram = new HashMap<>();
        pram.put("other_server_id", network);
        pram.put("org_id", orgId);
        List<GatewayPro> list = iGatewayProService.selectByMap(pram);

        apiResult.setData(list);
        return apiResult;
    }

    /**
     * 查询网关配置文件列表
     *
     * @param
     * @return
     */

    @ApiOperation(value = "查询网关配置文件列表", notes = "查询网关配置文件列表")
    @PostMapping(value = "/findGatewayPro")
    public ApiResult findGateway(@RequestBody RequestVO requestVO) {

        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);

        Integer currentPage = requestVO.getPageInfo().getCurrentPage();
        Integer pageSize = requestVO.getPageInfo().getPageSize();

        Map<String, Object> params = (Map<String, Object>) requestVO.getData().get("gatewayPro");
        GatewayPro gatewayProfile = JSON.parseObject(JSON.toJSONString(params), GatewayPro.class);
        Page<GatewayPro> allGatewayPro = iGatewayProService.findAllGatewayPro(currentPage, pageSize, gatewayProfile);
        apiResult.setData(allGatewayPro);
        return apiResult;
    }

    /**
     * 查询网关配置详情
     *
     * @param
     * @return
     */

    @ApiOperation(value = "查询网关配置配置详情", notes = "查询网关配置详情")
    @PostMapping(value = "/findGatewayInfo")
    public ApiResult findGatewayInfo(@RequestBody RequestVO requestVO) {

        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);

        String gatewayPro = (String) requestVO.getData().get("gatewayPro");
        Map<String, Object> pram = new HashMap<>();
        pram.put("gateway_profile_id", gatewayPro);
        List<GatewayPro> gatewayProfiles = iGatewayProService.selectByMap(pram);
        GatewayPro gatewayProfile = gatewayProfiles.get(0);
        //查询额外通道
        List<GatewayProChannel> gatewayProfileChannels = iGatewayProChannelService.selectByMap(pram);
        gatewayProfile.setGatewayProChannel(gatewayProfileChannels);
        gatewayProfile.setTotal(String.valueOf(gatewayProfileChannels.size()));
        apiResult.setData(gatewayProfile);
        return apiResult;
    }


    /**
     * 对外删除网关配置接口
     *
     * @param requestVO
     * @return
     */

    @MyLog(describe = "删除网关配置")
    @ApiOperation(value = "删除网关配置", notes = "删除网关配置接口")
    @PostMapping(value = "/deleteGatePro")
    public ApiResult deleteGate(@RequestBody RequestVO requestVO) {

        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);

        if (currentUser.getRole().contains(MainConstants.SYS)) {
            String gatewayPro = (String) requestVO.getData().get("gatewayPro");
            int i = iGatewayProService.deleteGatewayPro(gatewayPro);
            if (i == 200) {
                return apiResult;
            } else if (i == 403) {
                apiResult.setMsg("配置文件下有网关删除失败");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
            apiResult.setMsg("服务不可用");
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;

    }

}

