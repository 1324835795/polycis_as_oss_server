package com.polycis.main.client.downdata;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.vo.DevDownDataPO;
import com.polycis.main.entity.vo.DeviceQueueItemRequest;
import org.springframework.web.bind.annotation.RequestBody;

public class DownDataClientFallback implements DownDataClient {


    /**
     * 线程共享对象apiResult,不能再向里边塞值了
     */
    ApiResult<String> apiResult = new ApiResult<>(CommonCode.ERROR);

    @Override
    public ApiResult downData(@RequestBody  DevDownDataPO dq) {
        return apiResult;
    }
}
