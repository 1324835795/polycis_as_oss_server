package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.admin.OssAdmin;

/**
 * <p>
 * 应用表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface ISysLogService extends IService<SysLogoPO> {
    void insertSysLog(SysLogoPO sysLogoPO);

    OssAdmin getAccountByToken(String token);

    Page<SysLogoPO> selectLogPage(RequestVO requestVO);
}
