package com.polycis.main.controller.dataspecs;

import com.polycis.main.client.dataspecs.DataSpecsFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/21
 * description : 描述
 */
@Slf4j
@RestController
@RequestMapping("/data/specs")
public class DataSpecsController {
    @Resource
    private DataSpecsFeignClient feign;
    @PostMapping("/save")
    public ApiResult save(@RequestBody ProductPropertyDTO dto){
        return feign.save(dto);
    }
    @PostMapping("/get/productid")
    public ApiResult findByProductId(@RequestBody ProductPropertyDTO dto){
        return feign.findByProductId(dto);
    }
    @PostMapping("/get/id")
    public ApiResult findById(@RequestBody ProductPropertyDTO dto){
        return feign.findById(dto);
    }
    @PostMapping("/remove/id")
    public ApiResult removeByid(@RequestBody ProductPropertyDTO dto){
        return feign.removeByid(dto);
    }
    @PostMapping("/modify/id")
    public ApiResult modifyById(@RequestBody Map<String,Object> map){
        return feign.modifyById(map);
    }
}
