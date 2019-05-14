package com.polycis.main.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.mapper.admin.OssAdminMapper;
import com.polycis.main.service.admin.IOssAdminService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-14
 */
@Service
public class OssAdminServiceImpl extends ServiceImpl<OssAdminMapper, OssAdmin> implements IOssAdminService {

    @Autowired
    private OssAdminMapper ossAdminMapper;
    @Override
    public OssAdmin isossAdmin(OssAdmin ossAdmin) {
        ossAdmin.setDelete(1);
        ossAdmin.setStart(1);
        List<OssAdmin> ossAdmins = ossAdminMapper.selectList(new EntityWrapper<OssAdmin>(ossAdmin));
        if (null != ossAdmins && !ossAdmins.isEmpty()) {
            return ossAdmins.get(0);
        }
        return null;
    }
}
