package com.polycis.main.client.protocollib;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.App;
import org.springframework.stereotype.Component;

/**
 * @author qiaokai
 * @date 19/4/19
 */
@Component
public class ProtocolFeignClientFallback implements ProtocolFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult query(ProtocolInfo info) {
        return apiResult;
    }

    @Override
    public ApiResult limit(ProtocolInfo info) {
        return apiResult;
    }

    @Override
    public ApiResult delete(ProtocolInfo info) {
        return apiResult;
    }
}
