package com.polycis.main.client.redis;

import com.polycis.main.common.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther qiaokai
 */
@Component(value = "RedisFeignClient")
@FeignClient(value = "polycis-redis-server", fallback = RedisFeignClientFallback.class)
public interface RedisFeignClient {

    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value="/set", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult set(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value, @RequestParam(value = "expire") Integer expire);

    @RequestMapping(value="/get", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult get(@RequestParam(value = "key") String key);

    @RequestMapping(value="/remove", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult remove(@RequestParam(value = "key") String key);

    @RequestMapping(value="/getandsetexpire", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult getandsetexpire(@RequestParam(value = "key") String key, @RequestParam(value = "expire") Integer expire);



}
