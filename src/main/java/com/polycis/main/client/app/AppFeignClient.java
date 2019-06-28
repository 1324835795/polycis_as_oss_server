package com.polycis.main.client.app;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.App;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther qiaokai
 */
@Component(value = "AppFeignClient")
@FeignClient(value = "${polycis-as-send-server}", fallback = AppFeignClientFallback.class)
public interface AppFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    //, consumes = MediaType.APPLICATION_JSON_VALUE
    @RequestMapping(value="/resource/app/create",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult create(App app);


    @RequestMapping(value = "/resource/app/update", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult update(App app);

    @RequestMapping(value = "/resource/app/delete/{id}",method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult delete(@PathVariable(value = "id") String id);


    @RequestMapping(value = "/resource/app/query/{id}", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult query(@PathVariable(value = "id") String id);
}
