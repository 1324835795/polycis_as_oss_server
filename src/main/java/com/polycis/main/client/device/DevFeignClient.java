package com.polycis.main.client.device;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.vo.DeviceDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @auther qiaokai
 */
@Component(value = "DevFeignClient")
@FeignClient(value = "polycis-ns-send-server", fallback = DevFeignClientFallback.class)
public interface DevFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/resource/device/create", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult create(@RequestBody DeviceDTO deviceDTO);

    @RequestMapping(value = "/resource/device/update/{platform}", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult update(@RequestBody DeviceDTO deviceDTO);

    @RequestMapping(value = "/resource/device/delete/{platform}/{deviceUuid}",method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult delete(@PathVariable(value = "platform") Integer platform,
                            @PathVariable(value = "deviceUuid") String deviceUuid);

    @RequestMapping(value = "/resource/device/query/{platform}/{id}", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult query(@PathVariable(value = "platform") Integer platform,
                           @PathVariable(value = "id") String id);
}
