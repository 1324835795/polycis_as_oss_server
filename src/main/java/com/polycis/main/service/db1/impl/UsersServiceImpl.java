package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.Users;
import com.polycis.main.mapper.db1.UsersMapper;
import com.polycis.main.service.db1.IUsersService;
import com.polycis.main.client.redis.RedisFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.polycis.main.common.CommonSubCode.ISV_UPLOAD_FAIL;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-29
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {


    @Autowired
    private RedisFeignClient redisFeignClient;


    @Autowired
    private UsersMapper usersMapper;


    /**
     * 查看是否是有此账户
     *
     * @param uss
     * @return
     */
    @Override
    public Users isUser(Users uss) {
        // 是否启用  1启用 0未启用
        uss.setIsDelete(1);
        uss.setIsStart(1);
        List<Users> users = usersMapper.selectList(new EntityWrapper<Users>(uss));
        if (null != users && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public Users setByUserid(Integer usid) {
        return this.selectById(usid);
    }


    @Override
    public Boolean updataUser(Users us) {
        return this.updataUser(us);
    }

    @Override
    public Page<Users> listuserByPage(int cupage, int cusize, Users us) {


        Page<Users> page = new Page<Users>(cupage, cusize);
        EntityWrapper<Users> wrus = new EntityWrapper<Users>();
        if (null != us.getName()) {
            wrus.like("name", us.getName(), SqlLike.DEFAULT);
            wrus.or().like("loginname", us.getName(), SqlLike.DEFAULT);
        }
        if (us.getOrg() != 1) {
            wrus.and().eq("org", us.getOrg());
        }
        //  wrus.setEntity(new Users());

        wrus.and().eq("is_delete", 1);

        wrus.orderBy("id desc");
        return this.selectPage(page, wrus);
    }


    @Override
    public Boolean checkLoginNameUnique(String loginName) {
        Users users = new Users();
        users.setIsDelete(1);
        users.setLoginname(loginName);
        List<Users> list = usersMapper.selectList(new EntityWrapper<Users>(users));
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public Users selectAdminByOrg(Integer orgId) {
        Users users = new Users();
        users.setOrg(orgId);
     //   users.setRole("sys");
        List<Users> list = this.selectList(new EntityWrapper<Users>(users));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static final String WINDOWS_PROFILES_PATH = "C:/super_meeting/profiles/";
    public static final String LINUX_PROFILES_PATH = "/root/super_meeting/profiles/";

    @Override
    public String updUserProfile(MultipartFile newProfile) {
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);

        // 根据Windows和Linux配置不同的头像保存路径
        String OSName = System.getProperty("os.name");
        String profilesPath = OSName.toLowerCase().startsWith("win") ? WINDOWS_PROFILES_PATH
                : LINUX_PROFILES_PATH;

        if (!newProfile.isEmpty()) {
            // 当前用户
            Users currentUser = RequestHolder.getCurrentUser();
            String profilePathAndNameDB = usersMapper.selectById(currentUser.getId()).getPicturepath();
            // 默认以原来的头像名称为新头像的名称，这样可以直接替换掉文件夹中对应的旧头像
            String newProfileName = profilePathAndNameDB;
            // 若头像名称不存在
            if (profilePathAndNameDB == null || "".equals(profilePathAndNameDB)) {
                newProfileName = profilesPath + System.currentTimeMillis() + newProfile.getOriginalFilename();
                // 路径存库
                currentUser.setPicturepath(newProfileName);
                usersMapper.updateById(currentUser);
            }
            // 磁盘保存
            BufferedOutputStream out = null;
            try {
                File folder = new File(profilesPath);
                if (!folder.exists())
                    folder.mkdirs();
                out = new BufferedOutputStream(new FileOutputStream(newProfileName));
                // 写入新文件
                out.write(newProfile.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                apiResult.setSub_code(ISV_UPLOAD_FAIL.getKey());
                return apiResult.toString();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return apiResult.toString();
        } else {
            return apiResult.toString();
        }

    }

    @Override
    public Users selectbytokne(String accessToken) {
        List<Users> users = this.selectList(new EntityWrapper<Users>().eq("token", accessToken));
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
