package com.polycis.main.service.db1.impl;

import com.polycis.main.entity.Org;
import com.polycis.main.mapper.db1.OrgMapper;
import com.polycis.main.service.db1.IOrgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织管理表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

}
