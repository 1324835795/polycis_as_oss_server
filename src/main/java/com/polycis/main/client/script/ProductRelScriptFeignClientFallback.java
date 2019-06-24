package com.polycis.main.client.script;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.script.ProductRelScriptBO;
import com.polycis.main.entity.script.ProductRelScriptDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class ProductRelScriptFeignClientFallback implements ProductRelScriptFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult apiResult = new ApiResult<>(CommonCode.ERROR);


    @Override
    public ApiResult<ProductRelScriptBO> get(ProductRelScriptDTO productRelScriptDTO) {
        return apiResult;
    }

    @Override
    public ApiResult<ProductRelScriptBO> save(ProductRelScriptDTO productRelScriptDTO) {
        return apiResult;
    }

    @Override
    public ApiResult<String> delete(ProductRelScriptDTO productRelScriptDTO) {
        return apiResult;
    }

    @Override
    public ApiResult<String> test(ProductRelScriptDTO productRelScriptDTO) {
        return apiResult;
    }
}
