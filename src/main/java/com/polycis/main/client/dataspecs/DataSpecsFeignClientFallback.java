package com.polycis.main.client.dataspecs;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.controller.dataspecs.ProductPropertyDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author qiaokai
 * @date 19/4/19
 */
@Component
public class DataSpecsFeignClientFallback implements DataSpecsFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult save(ProductPropertyDTO dto) {
        return apiResult;
    }

    @Override
    public ApiResult findByProductId(ProductPropertyDTO dto) {
        return apiResult;
    }

    @Override
    public ApiResult findById(ProductPropertyDTO dto) {
        return apiResult;
    }

    @Override
    public ApiResult removeByid(ProductPropertyDTO dto) {
        return apiResult;
    }

    @Override
    public ApiResult modifyById(Map<String, Object> map) {
        return apiResult;
    }
}
