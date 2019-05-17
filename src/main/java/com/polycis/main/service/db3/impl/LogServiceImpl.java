package com.polycis.main.service.db3.impl;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.DevLogUp;
import com.polycis.main.mapper.db3.LogMapper;
import com.polycis.main.service.db3.IlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
@Service
public class LogServiceImpl implements IlogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public ApiResult<Map<String, Object>> selectSysLogInfo(RequestVO requestVO) {
        List<Map<String,Object>> list = logMapper.selectSysLog(requestVO);
        int total = logMapper.selectSysLogCount(requestVO);
        Map<String,Object> map = new HashMap<>(16);
        map.put("records",list);
        map.put("total",total);
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(map);
        return apiResult;
    }

    @Override
    public ApiResult<Map<String, Object>> selectDevLogState(RequestVO requestVO) {
        List<Map<String,Object>> list = logMapper.selectDevLogState(requestVO);
        int total = logMapper.selectDevLogStateCount(requestVO);
        Map<String,Object> map = new HashMap<>(16);
        map.put("records",list);
        map.put("total",total);
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(map);
        return apiResult;
    }

    @Override
    public ApiResult<Map<String, Object>> selectDevLogUp(RequestVO requestVO) {
        List<DevLogUp> list = logMapper.selectDevLogUp(requestVO);
        int total = logMapper.selectDevLogUpCount(requestVO);
        Map<String,Object> map = new HashMap<>(16);
        map.put("records",list);
        map.put("total",total);
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(map);
        return apiResult;
    }
    @Override
    public ApiResult<Map<String, Object>> selectDevLogDown(RequestVO requestVO) {
        List<DevLogUp> list = logMapper.selectDevLogDown(requestVO);
        int total = logMapper.selectDevLogDownCount(requestVO);
        Map<String,Object> map = new HashMap<>(16);
        map.put("records",list);
        map.put("total",total);
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        apiResult.setData(map);
        return apiResult;
    }
}
