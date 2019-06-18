package com.polycis.main.client.product;

import com.polycis.main.client.app.AppFeignClientFallback;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther 崔文昊
 */
@Component(value = "ProductFeignClient")
@FeignClient(value = "${polycis-as-send-server}", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {
    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */

    @RequestMapping(value="/resource/product/create",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult create(Product product);


    @RequestMapping(value = "/resource/product/update", produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult update(Product product);

    @RequestMapping(value = "/resource/product/delete/{id}",method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult delete(@PathVariable(value = "id") String id);

}
