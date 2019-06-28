package com.polycis.main.controller.script;


import com.polycis.main.client.script.ProductRelScriptFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.script.ProductRelScriptBO;
import com.polycis.main.entity.script.ProductRelScriptDTO;
import com.polycis.main.service.db1.script.IProductRelScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Js脚本前端控制器
 * </p>
 *
 * @author cg
 * @since 2019-06-19
 */
@RestController
@RequestMapping("/script/js")
public class ProductRelScriptController {
    private static final Logger logger = LoggerFactory.getLogger(ProductRelScriptController.class);

    @Autowired
    private IProductRelScriptService productRelScriptService;
    @Autowired
    private ProductRelScriptFeignClient productRelScriptFeignClient;

    /**
     * 获取js脚本
     * @param productRelScriptDTO
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<ProductRelScriptBO> get(@RequestBody ProductRelScriptDTO productRelScriptDTO) {
        ApiResult<ProductRelScriptBO> apiResult = this.productRelScriptFeignClient.get(productRelScriptDTO);
        return apiResult;
    }

    /**
     * 新增js脚本
     * @param productRelScriptDTO
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<ProductRelScriptBO> save(@RequestBody ProductRelScriptDTO productRelScriptDTO) {
        ApiResult<ProductRelScriptBO> apiResult = this.productRelScriptFeignClient.save(productRelScriptDTO);
        return apiResult;
    }


    /**
     * 删除js脚本
     * @param productRelScriptDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> delete(@RequestBody ProductRelScriptDTO productRelScriptDTO) {
        ApiResult<String> apiResult = this.productRelScriptFeignClient.delete(productRelScriptDTO);
        return apiResult;
    }

    /**
     * 测试js脚本
     * @param productRelScriptDTO
     * @return
     */
    @RequestMapping(value = "/test", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> test(@RequestBody ProductRelScriptDTO productRelScriptDTO) {
        ApiResult<String> apiResult = this.productRelScriptFeignClient.test(productRelScriptDTO);
        return apiResult;
    }

}
