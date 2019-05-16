package com.polycis.main.service.db1;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Notice;

import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
public interface INoticeService {
    ApiResult createNoticeMsg(Notice notice);
    ApiResult<Map<String,Object>> selectNoticeMsg(RequestVO requestVO);
    ApiResult updateInfoDead(Map map);
}
