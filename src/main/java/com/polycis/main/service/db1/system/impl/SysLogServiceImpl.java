package com.polycis.main.service.db1.system.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.mapper.db1.SysLogMapper;
import com.polycis.main.service.db1.system.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
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
@Lazy
public class SysLogServiceImpl  implements ISysLogService {

    protected static Logger LOG = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private RedisFeignClient redisFeignClient;

    @Override
    public void insertSysLog(SysLogoPO sysLogoPO) {
        sysLogMapper.insert(sysLogoPO);
    }


    @Override
    public OssAdmin getAccountByToken(String token) {
        ApiResult apiResult = redisFeignClient.get(token);
//        ApiResult apiResult = null;
        if (apiResult.getCode() != CommonCode.SUCCESS.getKey()) {
            LOG.info(String.format("根据token:%s,从redis缓存中获取用户信息失败！",token));
            return null;
        }

        String userJson = apiResult.getData().toString();
        if(StringUtils.isBlank(userJson)){
            LOG.info(String.format("根据用户缓存数据：%s，反序列化用户信息失败！",userJson));
            return null;
        }
        return JSON.parseObject(userJson, new TypeToken<OssAdmin>(){}.getType());
    }
}
