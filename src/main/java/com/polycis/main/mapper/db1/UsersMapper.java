package com.polycis.main.mapper.db1;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.polycis.main.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2018-11-29
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    Integer updateByOrg(Users users2);

    Users selectSysUserByOrgId(Integer organizationId);

    void insertTest();
}
