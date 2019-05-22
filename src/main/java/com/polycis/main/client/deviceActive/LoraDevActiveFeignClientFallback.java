package com.polycis.main.client.deviceActive;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraDeviceActivationDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraDevActiveFeignClientFallback implements LoraDevActiveFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult<String> abp(LoraDeviceActivationDTO tempDeviceActivition) {
        return apiResult;
    }

    @Override
    public ApiResult<String> otaa(LoraDeviceActivationDTO tempDeviceActivition) {
        return apiResult;
    }
}
