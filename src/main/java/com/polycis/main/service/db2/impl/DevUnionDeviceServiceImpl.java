package com.polycis.main.service.db2.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.db2.DevUnionDevice;
import com.polycis.main.entity.vo.AppVo;
import com.polycis.main.mapper.db2.DevUnionDeviceMapper;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.polycis.main.service.db1.IAppService;
import com.polycis.main.service.db2.IDevUnionDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
@Service
public class DevUnionDeviceServiceImpl extends ServiceImpl<DevUnionDeviceMapper, DevUnionDevice> implements IDevUnionDeviceService {

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IDevUnionDeviceService iDevUnionDeviceService;
    @Autowired
    private DevUnionDeviceMapper devUnionDeviceMapper;

   /* @Override
    public Integer selectOnLineDevice(Users currentUser) {

        List<String> list =iAppOrgRelationService.selectAppEui(currentUser.getOrg());

        list.forEach(s-> System.out.println(s));
        EntityWrapper<DevUnionDevice> devUnionDeviceEntityWrapper = new EntityWrapper<>();
        devUnionDeviceEntityWrapper.in("dev_union_device",list);
        devUnionDeviceEntityWrapper.eq("status",1);
        int i = iDevUnionDeviceService.selectCount(devUnionDeviceEntityWrapper);
        return i;

    }*/

    @Override
    public Integer selectOnLineDevice(Users currentUser, List<String> list) {
        //list.forEach(s-> System.out.println(s));
        EntityWrapper<DevUnionDevice> devUnionDeviceEntityWrapper = new EntityWrapper<>();
        if(list.size()==0){
            devUnionDeviceEntityWrapper.eq("app_eui","不存在po");
        }else {
            devUnionDeviceEntityWrapper.in("app_eui",list);
        }

        devUnionDeviceEntityWrapper.eq("status",1);
        int i = iDevUnionDeviceService.selectCount(devUnionDeviceEntityWrapper);
        return i;

    }

    @Override
    public List<Map<String, Integer>> selectDevonline(App app) {

      return   devUnionDeviceMapper.selectDevonline(app.getAppEui());
    }

    @Override
    public AppVo selectAppVo(AppVo appVo) {
        // 设备总数已经有了,不用再次查询了,这样写是因为忘了
        return devUnionDeviceMapper.selectAppVo(appVo.getAppEui());
    }
}
