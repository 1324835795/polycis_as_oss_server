package com.polycis.main.mapper.db1;

import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Notice;
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
public interface NoticeMapper {

    Integer insertNotice(Notice notice);
    List<Notice> selectNotice(RequestVO requestVO);
    Integer selectNoticeCount(RequestVO requestVO);
    Integer updateDeadTime(Map map);

}
