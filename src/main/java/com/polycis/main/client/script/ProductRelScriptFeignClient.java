package com.polycis.main.client.script;

import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.script.ProductRelScriptBO;
import com.polycis.main.entity.script.ProductRelScriptDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther qiaokai
 */
@Component(value = "ProductRelScriptFeignClient")
@FeignClient(value = "${polycis-data-process-server}", fallback = ProductRelScriptFeignClientFallback.class)
public interface ProductRelScriptFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/script/js/get", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<ProductRelScriptBO> get(@RequestBody ProductRelScriptDTO productRelScriptDTO);

    @RequestMapping(value = "/script/js/save", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<ProductRelScriptBO> save(@RequestBody ProductRelScriptDTO productRelScriptDTO);


    @RequestMapping(value = "/script/js/delete", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> delete(@RequestBody ProductRelScriptDTO productRelScriptDTO);

    @RequestMapping(value = "/script/js/test", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> test(@RequestBody ProductRelScriptDTO productRelScriptDTO);
}
