package com.polycis.main.service.db3.impl;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.WarnLevel;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.mapper.db3.DevDataWarnMapper;
import com.polycis.main.service.db3.IDevDataWarnService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-25
 */
@Service
public class DevDataWarnServiceImpl extends ServiceImpl<DevDataWarnMapper, DevDataWarn> implements IDevDataWarnService {
    @Resource
    private DevDataWarnMapper warnMapper;

    @Override
    public ApiResult<Map<String, Object>> selectWarnInfo(RequestVO param) {
        int total = warnMapper.selectWarnInfoCount(param);
        List<Map<String, Object>> data = warnMapper.selectWarnInfo(param);
        Map<String, Object> map = new HashMap<>(16);
        map.put("list", data);
        map.put("total", total);
        ApiResult<Map<String, Object>> api = new ApiResult<>();
        api.setCode(10000);
        api.setData(map);
        return api;
    }

    @Override
    public ApiResult updateWarnRead(RequestVO param) {
        int size = warnMapper.updateWarnRead(param.getData());
        ApiResult api = new ApiResult();
        api.setCode(10000);
        api.setMsg("成功标记" + size + "条数据");
        return api;
    }

    @Override
    public ApiResult<List<Map<String, Object>>> warnStatusCal(RequestVO param) {
        ApiResult<List<Map<String, Object>>> api = new ApiResult<>();
        api.setCode(10000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if("1".equals(param.getData().get("week").toString())){
            Calendar c = Calendar.getInstance();
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            Date d = c.getTime();
            String day = format.format(d);
            param.getData().put("beginTime",day);
        }
        List<Map<String, Object>> list = warnMapper.warnStatusCal(param);
        List<Map<String, Object>> listChange = new ArrayList<>();
        Map<String,Object> map0 = new HashMap<String,Object>();
        map0.put("label","未处理");
        map0.put("value",0);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("label","处理中");
        map1.put("value",0);
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("label","已处理");
        map2.put("value",0);
        listChange.add(map0);
        listChange.add(map1);
        listChange.add(map2);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < listChange.size(); j++) {
                if(list.get(i).get("label").toString().equals(listChange.get(j).get("label").toString())){
                    listChange.get(j).put("value",list.get(i).get("value"));
                }
            }
        }
        api.setData(listChange);
        return api;
    }

    @Override
    public ApiResult createWarnLevel(WarnLevel warnLevel) {
        if(warnMapper.selectWarnLevelEmpty(warnLevel)==0){
            warnMapper.insertWarnLevel(warnLevel);
        }else {
            warnMapper.updateWarnLevel(warnLevel);
        }
        ApiResult api = new ApiResult();
        api.setCode(10000);
        api.setMsg("操作成功");
        return api;
    }

    @Override
    public ApiResult<Map<String,Object>> selectWarnLevel(RequestVO requestVO) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("total",warnMapper.selectWarnLevelCount());
        map.put("records",warnMapper.selectWarnLevel(requestVO));
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>();
        apiResult.setCode(10000);
        apiResult.setData(map);
        return apiResult;
    }
}
