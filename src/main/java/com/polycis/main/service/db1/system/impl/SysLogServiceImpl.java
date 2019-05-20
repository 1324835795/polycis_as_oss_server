package com.polycis.main.service.db1.system.impl;

import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.mapper.db1.SysLogMapper;
import com.polycis.main.service.db1.system.ISysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 系统日志服务
 * </p>
 *
 * @author weitao
 * @since 2019-04-19
 */
@Service
public class SysLogServiceImpl  implements ISysLogService {

    protected static Logger LOG = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Resource
    SysLogMapper sysLogMapper;

    @Override
    public void insertSysLog(SysLogoPO sysLogoPO) {
        sysLogMapper.insert(sysLogoPO);
    }
}
