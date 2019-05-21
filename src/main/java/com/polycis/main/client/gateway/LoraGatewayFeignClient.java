package com.polycis.main.client.gateway;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.lora.LoraGatewayDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther cheng
 */
@Component(value = "LoraGatewayFeignClient")
@FeignClient(value = "polycis-ns-lora-server", fallback = LoraGatewayFeignClientFallback.class)
public interface LoraGatewayFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "${API_V2}/gateway/post", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> post(@RequestBody LoraGatewayDTO gw);

    @RequestMapping(value = "${API_V2}/gateway/put", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> put(@RequestBody LoraGatewayDTO gw);

    @RequestMapping(value = "${API_V2}/gateway/delete",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> delete(@RequestBody LoraGatewayDTO gw);

}
