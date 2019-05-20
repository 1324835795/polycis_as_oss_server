package com.polycis.main.service.db1.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.App;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.vo.AppVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface ISysLogService {
    void insertSysLog(SysLogoPO sysLogoPO);

    OssAdmin getAccountByToken(String token);
}
