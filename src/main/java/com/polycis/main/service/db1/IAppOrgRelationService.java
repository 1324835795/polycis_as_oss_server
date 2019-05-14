package com.polycis.main.service.db1;

import com.polycis.main.entity.AppOrgRelation;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 应用与组织关联表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IAppOrgRelationService extends IService<AppOrgRelation> {
    List<String> selectAppEui(Integer org);

    List<Integer> selecctAppId(Integer org, String description);
}
