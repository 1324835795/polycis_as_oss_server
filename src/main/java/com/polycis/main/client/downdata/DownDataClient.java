package com.polycis.main.client.downdata;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.vo.DevDownDataPO;
import com.polycis.main.entity.vo.DeviceQueueItemRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @auther cuiwenhao
 */
@Component(value = "DevFeignClient")
@FeignClient(value = "polycis-ns-send-server", fallback = DownDataClientFallback.class)
public interface DownDataClient {
    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     * @param
     * @return
     */

    @RequestMapping(value = "/down/data", method = {RequestMethod.POST},consumes = MediaType.APPLICATION_JSON_VALUE, produces = {"application/json;charset=UTF-8"})
    ApiResult downData(@RequestBody DevDownDataPO devDownDataPO );

}
