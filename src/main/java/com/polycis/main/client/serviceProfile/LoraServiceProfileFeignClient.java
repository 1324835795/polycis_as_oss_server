package com.polycis.main.client.serviceProfile;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.LoraServiceProfileDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther cheng
 */
@Component(value = "LoraServiceProfileFeignClient")
@FeignClient(value = "polycis-ns-lora-server", fallback = LoraServiceProfileFeignClientFallback.class)
public interface LoraServiceProfileFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "${API_V2}/service/profile/post", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> post(@RequestBody LoraServiceProfileDTO spFile);

    @RequestMapping(value = "${API_V2}/service/profile/put", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> put(@RequestBody LoraServiceProfileDTO spFile);

    @RequestMapping(value = "${API_V2}/service/profile/delete",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> delete(@RequestBody LoraServiceProfileDTO spFile);

}
