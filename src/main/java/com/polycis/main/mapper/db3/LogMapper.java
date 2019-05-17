package com.polycis.main.mapper.db3;

import com.polycis.main.common.page.RequestVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
@Mapper
public interface LogMapper {

    List<Map<String,Object>> selectSysLog(RequestVO requestVO);
    Integer selectSysLogCount(RequestVO requestVO);
    List<Map<String,Object>> selectDevLogState(RequestVO requestVO);
    Integer selectDevLogStateCount(RequestVO requestVO);
}
