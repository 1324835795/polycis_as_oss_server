package com.polycis.main.service.db1.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.gatewayProfile.LoraGatewayProfileFeignClient;
import com.polycis.main.client.initResource.LoraInitResourceFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.GatewayPro;
import com.polycis.main.entity.GatewayProChannel;
import com.polycis.main.entity.lora.Gateway;
import com.polycis.main.entity.lora.LoraGatewayProfileChannelDTO;
import com.polycis.main.entity.lora.LoraGatewayProfileDTO;
import com.polycis.main.entity.vo.GatewayProVO;
import com.polycis.main.mapper.db1.GatewayProMapper;
import com.polycis.main.service.db1.IGatewayProChannelService;
import com.polycis.main.service.db1.IGatewayProService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.service.db1.lora.IGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected static Logger Log = LoggerFactory.getLogger(GatewayProServiceImpl.class);
    @Autowired
    IGatewayProChannelService gatewayProChannelService;
    @Autowired
    IGatewayService iGatewayService;
    @Autowired
    IGatewayProService iGatewayProService;
    @Autowired
    LoraGatewayProfileFeignClient loraGatewayProfileFeignClient;
    @Autowired
    LoraInitResourceFeignClient loraInitResourceFeignClient;

    @Override
    public Boolean
    addGatewayPro(Integer orgId, GatewayProVO gatewayPro) {
        //首先去GoService注册网关配置文件
        //对象转换
        LoraGatewayProfileDTO dto = this.transformation(gatewayPro);
        ApiResult<String> initNetworkId = loraInitResourceFeignClient.getInitNetworkId();
        dto.setNetworkServerID(initNetworkId.getData());
        ApiResult<String> post = loraGatewayProfileFeignClient.post(dto);
        String proId = post.getData();
        System.out.println(proId);
        if(post.getCode()==10000){

            GatewayPro gp = new GatewayPro();
            gp.setName(gatewayPro.getName());
            gp.setGatewayProfileId(proId);
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
                gatewayProfileChannel.setGatewayProfileId(proId);
                gatewayProChannelService.insert(gatewayProfileChannel);
            }

            return insert;
        }
        return false;
    }

    @Override
    public int deleteGatewayPro(String gatewayPro) {

        //判断网关配置文件下是否有网关
        Map<String,Object> pram =new HashMap<>();
        pram.put("gatewayProfileID",gatewayPro);

        List<Gateway> gateways = iGatewayService.selectByMap(pram);
        if(gateways.size()==0){
            Log.info("网关配置文件无网关");
            //调用第三方将接口
            /*boolean flag =otherGatewayService.deleteGatewayPro(user,GatewayPro);*/
            LoraGatewayProfileDTO dto = new LoraGatewayProfileDTO();
            dto.setId(gatewayPro);
            ApiResult<String> post = loraGatewayProfileFeignClient.delete(dto);
            if(post.getCode()==10000){
                Map<String,Object> pram2 =new HashMap<>();
                pram2.put("gateway_profile_id",gatewayPro);
                boolean b1 = iGatewayProService.deleteByMap(pram2);
                boolean b = gatewayProChannelService.deleteByMap(pram2);
                if(b&&b1){
                    //删除网关信息到数据库
                    return 200;
                }
            }
            return 0;
        }
        Log.info("有网关不能删除");
        return 403;
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


    public LoraGatewayProfileDTO transformation(GatewayProVO gatewayPro){
        //对象转换
        LoraGatewayProfileDTO dto = new LoraGatewayProfileDTO();
        dto.setName(gatewayPro.getName());

        if(gatewayPro.getChannels()!=null&&gatewayPro.getChannels().length>0){
            String Str =Arrays.toString(gatewayPro.getChannels());
            Str = Str.substring(1,Str.length()-1);
            dto.setChannelsStr(Str);
        }

        List<LoraGatewayProfileChannelDTO> list = new ArrayList<>();
        String json2 = JSONArray.toJSONString(gatewayPro.getExtraChannels());
        List<GatewayProChannel> test = JSON.parseArray(json2,GatewayProChannel.class);
        for(int i = 0 ; i < test.size() ; i++) {
            GatewayProChannel gatewayProChannel = test.get(i);
            LoraGatewayProfileChannelDTO channelDTO = new LoraGatewayProfileChannelDTO(gatewayProChannel);
            list.add(channelDTO);
        }
        dto.setExtraChannels(list);

        return dto;
    }
}
