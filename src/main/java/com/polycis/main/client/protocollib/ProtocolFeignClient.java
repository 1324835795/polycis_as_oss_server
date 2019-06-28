package com.polycis.main.client.protocollib;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.App;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


/**
 * @author Wenyu Zhou
 */
@Component(value = "ProtocolFeignClient")
@FeignClient(value = "polycis-data-process-server", fallback = ProtocolFeignClientFallback.class)
public interface ProtocolFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     */
    @RequestMapping(value="/protocol/query",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult query(ProtocolInfo info);

    @RequestMapping(value="/protocol/limit",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult limit(ProtocolInfo info);

    @RequestMapping(value="/protocol/delete",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult delete(ProtocolInfo info);
    @RequestMapping(value="/protocol/detail",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult detail(Map map);
}
