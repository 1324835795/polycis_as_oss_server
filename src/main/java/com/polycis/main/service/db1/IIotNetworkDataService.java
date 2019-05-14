package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.IotNetworkData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
public interface IIotNetworkDataService extends IService<IotNetworkData> {

    Page likeDevData(Integer currentPage, Integer pageSize, String deveui);

    Boolean rmNetworkdatas(String params);
}
