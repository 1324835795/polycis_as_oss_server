package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.entity.App;
import com.polycis.main.entity.AppOrgRelation;
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

import java.util.HashMap;
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
    public Page<App> queryAppList(Integer currentPage, Integer pageSize, App app) {

        Page<App> page = new Page<App>(currentPage, pageSize);

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("pageNumber", (currentPage - 1) * pageSize);

        param.put("pageSize", pageSize);

        if (null != app.getDescription() && !"".equals(app.getDescription())) {
            param.put("query", app.getDescription());
        } else {
            param.put("query", null);
        }

        if (null != app.getId() && !"".equals(app.getId())) {
            param.put("orgId", app.getId());
        } else {
            param.put("orgId", null);
        }

        List<App> list = appMapper.queryAppList(param);

        Integer count = appMapper.queryAppListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;


    }

    @Override
    public  List<Map<String, Object>> selectProduct(App app) {

      List<Map<String, Object>> list = appMapper.selectProduct(app.getId());

      return list;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteApp(App app) {

        try {
            app.setIsDelete(MainConstants.DELETETED);
            iAppService.update(app, new EntityWrapper<App>().eq("id", app.getId()));
            iAppOrgRelationService.delete(new EntityWrapper<AppOrgRelation>().eq("app_id",app.getId()));
        } catch (Exception e) {
            LOG.info("删除出错:{}",app.toString());
            // 用以回滚
            throw  new RuntimeException();
        }


    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addApp(App app) {
        boolean insert = iAppService.insert(app);
        AppOrgRelation appOrgRelation = new AppOrgRelation();
        appOrgRelation.setAppId(app.getId());
        appOrgRelation.setOrganizationId(app.getOrganizationId());
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
