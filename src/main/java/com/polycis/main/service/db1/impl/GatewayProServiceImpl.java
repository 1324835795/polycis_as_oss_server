package com.polycis.main.service.db1.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.GatewayPro;
import com.polycis.main.entity.GatewayProChannel;
import com.polycis.main.entity.vo.GatewayProVO;
import com.polycis.main.mapper.db1.GatewayProMapper;
import com.polycis.main.service.db1.IGatewayProChannelService;
import com.polycis.main.service.db1.IGatewayProService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@Service
public class GatewayProServiceImpl extends ServiceImpl<GatewayProMapper, GatewayPro> implements IGatewayProService {


    @Autowired
    IGatewayProChannelService gatewayProChannelService;

    @Override
    public Boolean addGatewayPro(Integer orgId, GatewayProVO gatewayPro) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        GatewayPro gp = new GatewayPro();
        gp.setName(gatewayPro.getName());
        gp.setGatewayProfileId(uuid.toString());
        gp.setCreateTime(new Date());
        gp.setDel(1);
        gp.setOtherServerId(gatewayPro.getNetworkServerID());
        gp.setOrgId(orgId);
        gp.setNetId(Integer.parseInt(gatewayPro.getNetId()));
        boolean insert = this.insert(gp);

        List<GatewayProChannel> extraChannels = gatewayPro.getExtraChannels();
        String json2 = JSONArray.toJSONString(gatewayPro.getExtraChannels());
        List<GatewayProChannel> test = JSON.parseArray(json2,GatewayProChannel.class);

        if(test==null || test.isEmpty()) {
            return insert;
        }
        for (int i=0;i<test.size();i++){
            GatewayProChannel gatewayProfileChannel = test.get(i);
                   /* GatewayProfileChannel ch = new GatewayProfileChannel();
                    ch.setBandwidth(gatewayProfileChannel.getBandwidth());
                    ch.setGatewayProfileId(gwProID);
                    ch.setFrequency(gatewayProfileChannel.getFrequency());*/
            System.out.println(test.get(i).getSpreadFactors());
            gatewayProfileChannel.setIsDelete(1);
            gatewayProfileChannel.setGatewayProfileId(uuid.toString());
            gatewayProChannelService.insert(gatewayProfileChannel);
        }

        return insert;
    }

    @Override
    public Boolean deleteGatewayPro(String gatewayPro) {

        /*//判断网关配置文件下是否有网关
        Map<String,Object> pram =new HashMap<>();
        pram.put("gateway_pro_id",GatewayPro);
        pram.put("is_delete",1);
        List<Gateway> gateways = iGatewayService.selectByMap(pram);
        if(gateways.size()==0){
            Log.info("网关配置文件无网关");
            //调用第三方将接口
            boolean flag =otherGatewayService.deleteGatewayPro(user,GatewayPro);
            if(flag){
                Map<String,Object> pram2 =new HashMap<>();
                pram2.put("gateway_profile_id",GatewayPro);
                iGatewayProfileService.deleteByMap(pram2);
                iGatewayProfileService.deleteByMap(pram2);
                //删除网关信息到数据库
                return 200;
            }
            return 0;
        }
        Log.info("有网关不能删除");
        return 403;
        */
        return null;
    }

    @Override
    public Page<GatewayPro> findAllGatewayPro(Integer currentPage, Integer pageSize, GatewayPro gateway) {


        Page<GatewayPro> page = new Page<>(currentPage, pageSize);
        EntityWrapper<GatewayPro> dev = new EntityWrapper<>();

        if (null!=gateway.getName()||!"".equals(gateway.getName())){
            dev.or().like("name",gateway.getName(), SqlLike.DEFAULT);
        }
        dev.eq("del",1);
        dev.orderBy("create_time desc");
        Page<GatewayPro> gateProPage = this.selectPage(page, dev);
        return gateProPage;

    }
}
