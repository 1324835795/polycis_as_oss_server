package com.polycis.main.client.product;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient{

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);
    @Override
    public ApiResult create(Product product) {
        return apiResult;
    }

    @Override
    public ApiResult update(Product product) {
        return apiResult;
    }

    @Override
    public ApiResult delete(String id) {
        return apiResult;
    }
}
