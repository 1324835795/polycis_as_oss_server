package com.polycis.main.client.gatewayProfile;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraGatewayProfileDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraGatewayProfileFeignClientFallback implements LoraGatewayProfileFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult<String> post(LoraGatewayProfileDTO gwFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> put(LoraGatewayProfileDTO gwFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> delete(LoraGatewayProfileDTO gwFile) {
        return apiResult;
    }
}
