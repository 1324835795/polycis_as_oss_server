package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.GatewayPro;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.vo.GatewayProVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public interface IGatewayProService extends IService<GatewayPro> {


    Boolean addGatewayPro(Integer orgId, GatewayProVO gatewayPro);

    int deleteGatewayPro(String gatewayPro);

    Page<GatewayPro> findAllGatewayPro(Integer currentPage, Integer pageSize, GatewayPro gatewayProfile);
}
