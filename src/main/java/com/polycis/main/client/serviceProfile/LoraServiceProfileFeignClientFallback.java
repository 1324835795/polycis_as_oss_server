package com.polycis.main.client.serviceProfile;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraServiceProfileDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraServiceProfileFeignClientFallback implements LoraServiceProfileFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult apiResult = new ApiResult<>(CommonCode.ERROR);


    @Override
    public ApiResult<String> post(LoraServiceProfileDTO spFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> put(LoraServiceProfileDTO spFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> delete(LoraServiceProfileDTO spFile) {
        return apiResult;
    }

    @Override
    public ApiResult<LoraServiceProfileDTO> get(LoraServiceProfileDTO spFile) {
        return apiResult;
    }
}
