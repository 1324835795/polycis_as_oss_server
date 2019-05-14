package com.polycis.main.client.device;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.vo.DeviceDTO;
import org.springframework.stereotype.Component;

/**
 * @author qiaokai
 * @date 19/4/19
 */
@Component
public class DevFeignClientFallback implements DevFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);


    @Override
    public ApiResult create(DeviceDTO deviceDTO) {
        return apiResult;
    }

    @Override
    public ApiResult update(DeviceDTO deviceDTO) {
        return apiResult;
    }

    @Override
    public ApiResult delete(Integer platform, String id) {
        return apiResult;
    }

    @Override
    public ApiResult query(Integer platform, String id) {
        return apiResult;
    }
}
