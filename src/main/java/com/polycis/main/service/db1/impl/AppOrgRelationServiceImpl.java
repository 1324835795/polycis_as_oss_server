package com.polycis.main.service.db1.impl;

import com.polycis.main.entity.AppOrgRelation;
import com.polycis.main.mapper.db1.AppOrgRelationMapper;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 应用与组织关联表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class AppOrgRelationServiceImpl extends ServiceImpl<AppOrgRelationMapper, AppOrgRelation> implements IAppOrgRelationService {


    @Autowired
    private AppOrgRelationMapper appOrgRelationMapper;


    @Override
    public List<String> selectAppEui(Integer org) {
       return  appOrgRelationMapper.selectAppEui(org);

    }

    @Override
    public List<Integer> selecctAppId(Integer org, String description) {
        return  appOrgRelationMapper.selecctAppId(org,description);
    }
}
