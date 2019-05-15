package com.polycis.main.service.db3;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.db3.DevDataWarn;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-25
 */
public interface IDevDataWarnService extends IService<DevDataWarn> {
    ApiResult<Map<String,Object>> selectWarnInfo(RequestVO param);
    ApiResult updateWarnRead(RequestVO param);
    ApiResult<List<Map<String, Object>>> warnStatusCal(RequestVO param);
}
