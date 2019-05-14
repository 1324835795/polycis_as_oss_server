package com.polycis.main.service.db1.impl;

import com.polycis.main.entity.Http;
import com.polycis.main.mapper.db1.HttpMapper;
import com.polycis.main.service.db1.IHttpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用的http信息表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class HttpServiceImpl extends ServiceImpl<HttpMapper, Http> implements IHttpService {

}
