package com.polycis.main.client.redis;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import org.springframework.stereotype.Component;

/**
 * @author qiaokai
 * @date 19/4/19
 */
@Component
public class RedisFeignClientFallback implements RedisFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult set(String key, String value, Integer expire) {
        return apiResult;
    }

    @Override
    public ApiResult get(String key) {
        return apiResult;
    }



    @Override
    public ApiResult remove(String key) {
        return apiResult;
    }

    @Override
    public ApiResult getandsetexpire(String key, Integer expire) {
        return apiResult;
    }
}
