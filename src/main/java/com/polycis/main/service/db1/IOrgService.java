package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.Org;

import java.util.Map;

/**
 * <p>
 * 组织管理表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-14
 */
public interface IOrgService extends IService<Org> {

    Map<String,Integer> selectConsumerCountInfo(Integer id);

    ApiResult addOrgAndUser(Org orgusers);
}
