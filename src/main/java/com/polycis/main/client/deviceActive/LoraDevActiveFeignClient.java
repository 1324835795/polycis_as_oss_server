package com.polycis.main.client.deviceActive;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.LoraDeviceActivationDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther cheng
 */
@Component(value = "LoraDevActiveFeignClient")
@FeignClient(value = "${polycis-ns-lora-server}", fallback = LoraDevActiveFeignClientFallback.class)
public interface LoraDevActiveFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "${API_V2}/dev/active/ABP", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> abp(@RequestBody LoraDeviceActivationDTO tempDeviceActivition);

    @RequestMapping(value = "${API_V2}/dev/active/OTAA", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> otaa(@RequestBody LoraDeviceActivationDTO tempDeviceActivition);

}
