package com.polycis.main.client.dataspecs;

import com.polycis.main.client.protocollib.ProtocolInfo;
import com.polycis.main.common.ApiResult;
import com.polycis.main.controller.dataspecs.ProductPropertyDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


/**
 * @author Wenyu Zhou
 */
@Component(value = "DataSpecsFeignClient")
@FeignClient(value = "${polycis-data-process-server}", fallback = DataSpecsFeignClientFallback.class)
public interface DataSpecsFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     */
    @RequestMapping(value="/data/specs/save",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult save(ProductPropertyDTO dto);
    @RequestMapping(value="/data/specs/get/productid",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult findByProductId(ProductPropertyDTO dto);
    @RequestMapping(value="/data/specs/get/id",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult findById(ProductPropertyDTO dto);
    @RequestMapping(value="/data/specs/remove/id",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult removeByid(ProductPropertyDTO dto);
    @RequestMapping(value="/data/specs/modify/id",method = {RequestMethod.POST},produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult modifyById(Map<String,Object> map);
}
