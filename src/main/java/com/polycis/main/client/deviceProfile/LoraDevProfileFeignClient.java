package com.polycis.main.client.deviceProfile;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.LoraDeviceProfileDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther cheng
 */
@Component(value = "DevProfileFeignClient")
@FeignClient(value = "polycis-ns-lora-server", fallback = LoraDevProfileFeignClientFallback.class)
public interface LoraDevProfileFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "${API_V2}/dev/profile/post", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> post(@RequestBody LoraDeviceProfileDTO dpFile);

    @RequestMapping(value = "${API_V2}/dev/profile/put", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> put(@RequestBody LoraDeviceProfileDTO dpFile);

    @RequestMapping(value = "${API_V2}/dev/profile/delete",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> delete(@RequestBody LoraDeviceProfileDTO dpFile);

}
