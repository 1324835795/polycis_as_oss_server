package com.polycis.main.service.db1.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.entity.bo.SysLogBo;
import com.polycis.main.mapper.db1.SysLogMapper;
import com.polycis.main.service.db1.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogoPO> implements ISysLogService {

    protected static Logger LOG = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private RedisFeignClient redisFeignClient;

    @Autowired
    private  ISysLogService iSysLogService;

    @Override
    public void insertSysLog(SysLogoPO sysLogoPO) {
        sysLogMapper.insert(sysLogoPO);
    }


    @Override
    public OssAdmin getAccountByToken(String token) {
        ApiResult apiResult = redisFeignClient.get(token);
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

    @Override
    public Page<SysLogoPO> selectLogPage(RequestVO requestVO) {


        SysLogBo sysLogBo = JSON.parseObject(JSON.toJSONString(requestVO.getData()), SysLogBo.class);

        EntityWrapper<SysLogoPO> wrapper = new EntityWrapper<>();
        wrapper.eq("org_id",sysLogBo.getOrgId());
        wrapper.eq("user_type",2);
        if(sysLogBo.getEndTime()!=null && sysLogBo.getStartTime()!=null
                && !"".equals(sysLogBo.getEndTime()) && !"".equals(sysLogBo.getStartTime())){
            wrapper.between("create_time",sysLogBo.getStartTime(),sysLogBo.getEndTime());
        }

        if(sysLogBo.getType()!=null && !"".equals(sysLogBo.getType()) ){
            wrapper.eq("operation",sysLogBo.getType());
        }

        if(sysLogBo.getName()!=null && !"".equals(sysLogBo.getName())){
            wrapper.eq("username",sysLogBo.getName());
        }
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Page<SysLogoPO> page = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<SysLogoPO> sysLogoPOPage = iSysLogService.selectPage(page, wrapper);


        return sysLogoPOPage;

    }
}
