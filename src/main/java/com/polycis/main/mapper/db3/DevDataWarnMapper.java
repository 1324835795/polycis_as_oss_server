package com.polycis.main.mapper.db3;

import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.WarnLevel;
import com.polycis.main.entity.db3.DevDataWarn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-25
 */
@Mapper
public interface DevDataWarnMapper extends BaseMapper<DevDataWarn> {

    List<Map<String,Object>> selectWarnInfo(RequestVO param);

    int selectWarnInfoCount(RequestVO param);

    int updateWarnRead(Map map);

    List<Map<String,Object>> warnStatusCal(RequestVO param);

    @Select("select count(*) from dev_warn_level where dev_type_id = #{devTypeId} and warn_type_id = #{warnTypeId}")
    int selectWarnLevelEmpty(WarnLevel warnLevel);

    int insertWarnLevel(WarnLevel warnLevel);
    @Update("update dev_warn_level set warn_level_id = #{warnLevelId} ,state = 1 where dev_type_id = #{devTypeId} and warn_type_id = #{warnTypeId}")
    int updateWarnLevel(WarnLevel warnLevel);

    List<WarnLevel> selectWarnLevel(RequestVO requestVO);
    Integer selectWarnLevelCount();
    @Update("update dev_warn_level set state = #{state} where id = #{id}")
    int updateWarnLevelState(Map map);
    @Delete("delete from dev_warn_level where id = #{id}")
    int deleteWarnLevelById(Map map);

    List<Map<String,Object>> selectAWeekAPIOss();

    Integer aweekapisum();
}
