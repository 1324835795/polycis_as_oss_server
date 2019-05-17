package com.polycis.main.service.db3;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;

import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
public interface IlogService {
    ApiResult<Map<String,Object>> selectSysLogInfo(RequestVO requestVO);
    ApiResult<Map<String,Object>> selectDevLogState(RequestVO requestVO);
    ApiResult<Map<String,Object>> selectDevLogUp(RequestVO requestVO);
    ApiResult<Map<String,Object>> selectDevLogDown(RequestVO requestVO);
}
