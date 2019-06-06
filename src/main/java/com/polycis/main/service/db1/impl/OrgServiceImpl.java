package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.netflix.discovery.converters.Auto;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.entity.*;
import com.polycis.main.entity.lora.Gateway;
import com.polycis.main.mapper.db1.DeviceMapper;
import com.polycis.main.mapper.db1.OrgMapper;
import com.polycis.main.service.db1.*;
import com.polycis.main.service.db1.lora.IGatewayService;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织管理表 服务实现类
 * </p>
 *
 * @author qiaokai
 * @since 2019-06-05
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

    @Autowired
    private IOrgService iOrgService;

    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IGatewayService iGatewayService;

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;


    /**
     *  查询用户详情里多种数量信息
     * @param id 组织id
     * @return
     */
    @Override
    public Map<String, Integer> selectConsumerCountInfo(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        int appCount = iAppOrgRelationService.selectCount(new EntityWrapper<AppOrgRelation>()
                .eq("organization_id", id));

        int productCount = iProductService.selectCount(new EntityWrapper<Product>()
                .eq("org", id).eq("is_delete", MainConstants.UN_DELETE));

        int gatewayCount = iGatewayService.selectCount(new EntityWrapper<Gateway>().eq("org_id", id));

        // 这里没有设置接口包装,体现了线程结束清除数据源的重要性
        Integer deviceCount = deviceMapper.selectOrgCount(id);

        List<String> list =deviceMapper.selectOrgDevList(id);
        Integer alarmCount = iMybatisPlusDB3Service.selectAppAlarm(list);
        map.put("appCount",appCount);
        map.put("productCount",productCount);
        map.put("gatewayCount",gatewayCount);
        map.put("deviceCount",deviceCount);
        map.put("alarmCount",alarmCount);
        return map;
    }

    @Override
    public ApiResult addOrgAndUser(Org orgusers) {
        ApiResult apiResult = new ApiResult<>();

        // 添加的客户只是超级管理客户,不能添加普通客户,需要接入平台自主维护
        boolean b = iOrgService.insert(orgusers);
        if (b) {
            //创建组织成功 继续创建客户
            Users user = new Users();
            user.setOrg(orgusers.getId());
            user.setLoginname(orgusers.getLoginname());
            user.setRole("susys");
            user.setType(3);
            user.setName(orgusers.getName());
            user.setPassword(orgusers.getPassword());
            user.setIsStart(1);
            boolean bb = iUsersService.insert(user);
            if(bb){
                return apiResult;
            }else{
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("账户登录名已被占用");
                return apiResult;
            }
        } else {
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg("账户名字已被占用");
            return apiResult;
        }


    }
}
