package com.polycis.main.client.initResource;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @date 19/4/19
 */
@Component
public class LoraInitResourceFeignClientFallback implements LoraInitResourceFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult apiResult = new ApiResult<>(CommonCode.ERROR);


    @Override
    public ApiResult<String> getInitNetworkId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInitLoraServerHost() {
        return apiResult;
    }

    @Override
    public ApiResult<Integer> getInitLoraHttpTimeout() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInitUsername() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInitUserPassword() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInitOrganizationId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInitServiceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_ABP_A_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_ABP_B_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_ABP_C_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_OTAA_A_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_OTAA_B_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getInit_OTAA_C_DeviceProfileId() {
        return apiResult;
    }

    @Override
    public ApiResult<String> getUplinkDataURL() {
        return apiResult;
    }

    @Override
    public ApiResult<Long> getTokenTimeout() {
        return apiResult;
    }
}
