package com.polycis.main.service.db1.impl;

import com.polycis.main.entity.GatewayPro;
import com.polycis.main.entity.vo.GatewayProVO;
import com.polycis.main.mapper.db1.GatewayProMapper;
import com.polycis.main.service.db1.IGatewayProService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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
        gp.setOrgId(gatewayPro.getOrganizationId());
        gp.setNetId(Integer.parseInt(gatewayPro.getNetId()));
        boolean insert = this.insert(gp);
        return insert;
    }
}
