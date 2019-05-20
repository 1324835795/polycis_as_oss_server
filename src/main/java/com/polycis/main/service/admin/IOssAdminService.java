package com.polycis.main.service.admin;

import com.polycis.main.entity.admin.OssAdmin;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-14
 */
public interface IOssAdminService extends IService<OssAdmin> {

    OssAdmin isossAdmin(OssAdmin ossAdmin);

}
