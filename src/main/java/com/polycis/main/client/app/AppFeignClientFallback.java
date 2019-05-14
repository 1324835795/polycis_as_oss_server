package com.polycis.main.client.app;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.App;
import org.springframework.stereotype.Component;

/**
 * @author qiaokai
 * @date 19/4/19
 */
@Component
public class AppFeignClientFallback implements AppFeignClient {

    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);


    @Override
    public ApiResult create(App app) {
        return apiResult;
    }

    @Override
    public ApiResult update(App app) {
        return apiResult;
    }

    @Override
    public ApiResult delete(String id) {
        return apiResult;
    }

    @Override
    public ApiResult query(String id) {
        return apiResult;
    }
}
