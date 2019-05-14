package com.polycis.main.service.db1;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 19/4/19
 */
public interface IUsersService extends IService<Users> {

    /**
     * 登录使用
     *
     * @param uss 必须传入登录名和密码
     * @return
     */
    Users isUser(Users uss);

    /**
     * 根据用户标识查询用户信息
     *
     * @param usid
     * @return
     */
    Users setByUserid(Integer usid);


    /**
     * 跟新用户信息
     *
     * @param us
     * @return
     */
    Boolean updataUser(Users us);

    /**
     * 查询用户列表
     *
     * @param us
     * @return
     */
    Page<Users> listuserByPage(int cupage, int cusize, Users us);

    /**
     * 根据token查询用户
     * @param accessToken
     * @return
     */
    Users selectbytokne(String accessToken);


    Boolean checkLoginNameUnique(String loginName);

    Users selectAdminByOrg(Integer orgId);

}
