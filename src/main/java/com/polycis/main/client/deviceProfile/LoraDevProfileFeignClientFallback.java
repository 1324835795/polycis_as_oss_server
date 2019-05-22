package com.polycis.main.client.deviceProfile;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.lora.LoraDeviceProfileDTO;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraDevProfileFeignClientFallback implements LoraDevProfileFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult<String> post(LoraDeviceProfileDTO dpFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> put(LoraDeviceProfileDTO dpFile) {
        return apiResult;
    }

    @Override
    public ApiResult<String> delete(LoraDeviceProfileDTO dpFile) {
        return apiResult;
    }
}
