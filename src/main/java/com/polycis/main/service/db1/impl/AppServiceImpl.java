package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.entity.App;
import com.polycis.main.entity.AppOrgRelation;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.vo.AppVo;
import com.polycis.main.mapper.db1.AppMapper;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.polycis.main.service.db1.IAppService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

    protected static Logger LOG = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private  AppMapper appMapper;
    @Autowired
    private  IAppService iAppService;

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;


    @Override
    public Page<App> queryAppList(Integer currentPage, Integer pageSize, Users currentUser, App app) {
        EntityWrapper<App> appEntityWrapper = new EntityWrapper<>();

        if(null!=app && null!=app.getName() && !app.getName().equals("")){

            appEntityWrapper.like("name",app.getName(), SqlLike.DEFAULT);
        }
        appEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
       appEntityWrapper.eq("organization_id",currentUser.getOrg());
        appEntityWrapper.orderBy("create_time desc");




        Page<App> page = new Page<App>(currentPage, pageSize);

        // aop切入
       return  iAppService.selectPage(page,appEntityWrapper);

    }

    @Override
    public  List<Map<String, Object>> selectProduct(Users currentUser, App app) {

      List<Map<String, Object>> list = appMapper.selectProduct(app.getId());

      return list;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteApp(Users currentUser, App app) {

        try {
            EntityWrapper<App> wrapper = new EntityWrapper<>();
            wrapper.eq("organization_id", currentUser.getOrg());
            wrapper.eq("id", app.getId());
            // 如果删除自己组织的,肯定能删除成功,删除别人组织的,不会成功也不会返回特殊code
            App app1 = new App();
            app1.setIsDelete(MainConstants.DELETETED);
            iAppService.update(app1, wrapper);

            EntityWrapper<AppOrgRelation> appOrgRelationEntityWrapper = new EntityWrapper<>();
            appOrgRelationEntityWrapper.eq("organization_id",currentUser.getOrg());
            appOrgRelationEntityWrapper.eq("app_id",app.getId());
            iAppOrgRelationService.delete(appOrgRelationEntityWrapper);
        } catch (Exception e) {
            LOG.info("删除出错:{}",app.toString());
            // 用以回滚
            throw  new RuntimeException();
        }


    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addApp(App app, Users currentUser) {
        boolean insert = iAppService.insert(app);
        AppOrgRelation appOrgRelation = new AppOrgRelation();
        appOrgRelation.setAppId(app.getId());
        appOrgRelation.setOrganizationId(currentUser.getOrg());
        iAppOrgRelationService.insert(appOrgRelation);
        return insert;
    }

    @Override
    public List<String> selctTest() {
       return baseMapper.selectTest();
    }

    @Override
    public List<AppVo> seletMonitorApp(Integer org) {
     return   baseMapper.seletMonitorApp(org);
    }

    @Override
    public Integer selectDevIsmine(Integer id, Integer org) {

        return baseMapper.selectDevIsmine(id,org);

    }


}
