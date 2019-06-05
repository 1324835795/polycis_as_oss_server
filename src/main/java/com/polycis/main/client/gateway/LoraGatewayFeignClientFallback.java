package com.polycis.main.client.gateway;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraGatewayDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraGatewayFeignClientFallback implements LoraGatewayFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult<String> post(LoraGatewayDTO gw) {
        return apiResult;
    }

    @Override
    public ApiResult<String> put(LoraGatewayDTO gw) {
        return apiResult;
    }

    @Override
    public ApiResult<String> delete(LoraGatewayDTO gw) {
        return apiResult;
    }

    @Override
    public ApiResult<LoraGatewayDTO> get(LoraGatewayDTO gw) {
        return apiResult;
    }


}
