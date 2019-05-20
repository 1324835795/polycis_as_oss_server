package com.polycis.main.mapper.db1;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.App;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.vo.AppVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogoPO> {

}
