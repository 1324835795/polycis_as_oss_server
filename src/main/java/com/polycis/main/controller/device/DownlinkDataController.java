package com.polycis.main.controller.device;


import com.alibaba.fastjson.JSON;
import com.polycis.main.client.downdata.DownDataClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.*;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.vo.DevDownDataPO;
import com.polycis.main.entity.vo.DownDataVO;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IDownlinkDataService;
import com.polycis.main.service.db1.IProductService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 设备下行数据表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@RestController
@RequestMapping("/downlinkData")
public class DownlinkDataController {

    protected static Logger Log = LoggerFactory.getLogger(DownlinkDataController.class);

    @Autowired
    private DownDataClient appFeignClient;
    @Autowired
    private IDownlinkDataService iDownlinkDataService;
    @Autowired
    private IDeviceService iDeviceService;
    @Autowired
    private IProductService iProductService;


    @PostMapping(value = "/downData")
    public ApiResult downData(@RequestBody RequestVO requestVO) {
        ApiResult apiResult = new ApiResult<>();
        try {
            Users currentUser = RequestHolder.getCurrentUser();
            if (currentUser.getRole().contains(MainConstants.SYS)){
                List<String> list = new ArrayList<>();
                boolean base64 = (boolean) requestVO.getData().get("isbase64");
                Map<String, Object> params = (Map<String, Object>) requestVO.getData().get("downData");
                DevDownDataPO dq = JSON.parseObject(JSON.toJSONString(params), DevDownDataPO.class);
                //单个设备下发
                String deviceUuid = dq.getDeviceUuid();
                //查询设备类型
                Map<String,Object> prarm = new HashMap<>();
                prarm.put("device_uuid",deviceUuid);
                List<Device> devices = iDeviceService.selectByMap(prarm);
                list.add(deviceUuid);
                dq.setDevUuidList(list);
                dq.setPlatform( devices.get(0).getPlatform());

                ApiResult result = iDownlinkDataService.downData(base64, dq);
                apiResult.setMsg(result.getMsg());
                apiResult.setCode(result.getCode());

            }else {
                apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
                apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            }
        } catch (Exception e) {
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg(CommonCode.ERROR.getValue());
            Log.error(e.toString());
        }
        return apiResult;
    }

    @PostMapping(value = "/downDataList")
    public ApiResult downDataList(@RequestBody RequestVO requestVO) {
        ApiResult apiResult = new ApiResult<>();
        try {
            Users currentUser = RequestHolder.getCurrentUser();
            if (currentUser.getRole().contains(MainConstants.SYS)){
                List<String> list = new ArrayList<>();
                boolean base64 = (boolean) requestVO.getData().get("isbase64");
                Integer product = (Integer) requestVO.getData().get("productId");
                Map<String, Object> params = (Map<String, Object>) requestVO.getData().get("downData");
                DevDownDataPO dq = JSON.parseObject(JSON.toJSONString(params), DevDownDataPO.class);
                //批量设备下发
                Device device = new Device();
                //查询产品下的所有设备
                Map<String,Object> dev = new HashMap<>();
                dev.put("product_id",product);
                List<Device> devices = iDeviceService.selectByMap(dev);
                for (int i=0;i<devices.size();i++){
                    String deviceUuid = devices.get(i).getDeviceUuid();
                    list.add(deviceUuid);
                }
                dq.setDevUuidList(list);
                Product product1 = iProductService.selectById(product);
                dq.setPlatform(product1.getPlatform());

                ApiResult result = iDownlinkDataService.downData(base64, dq);
                apiResult.setMsg(result.getMsg());
                apiResult.setCode(result.getCode());

            }else {
                apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
                apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            }
        } catch (Exception e) {
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg(CommonCode.ERROR.getValue());
            Log.error(e.toString());
        }
        return apiResult;
    }


    @ApiOperation(value = "查看下行数据列表", notes = "查看下行数据列表")
    @RequestMapping(value = "/downdataList", method = RequestMethod.POST)
    public ApiResult addApp(@RequestBody RequestVO requestVO) {
        Users currentUser = RequestHolder.getCurrentUser();

        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Integer productId = (Integer)requestVO.getData().get("productId");

        List<DownlinkData> page = iDownlinkDataService.queryDataList(currentPage, pageSize, currentUser, productId);
        Integer integer = iDownlinkDataService.queryDataCount(productId);

        DownDataVO vo = new DownDataVO();
        vo.setRecords(page);
        vo.setTotal(integer);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(vo);
        return apiResult;
    }


}


