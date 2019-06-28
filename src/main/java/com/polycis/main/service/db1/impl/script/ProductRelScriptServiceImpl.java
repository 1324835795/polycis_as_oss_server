package com.polycis.main.service.db1.impl.script;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.entity.script.ProductRelScriptBO;
import com.polycis.main.mapper.db1.ProductRelScriptMapper;
import com.polycis.main.service.db1.script.IProductRelScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  js脚本服务实现类
 * </p>
 *
 * @author cg
 * @since 2019-06-19
 */
@Transactional
@Service("ProductRelScriptService")
public class ProductRelScriptServiceImpl extends ServiceImpl<ProductRelScriptMapper, ProductRelScriptBO> implements IProductRelScriptService {

    @Autowired
    private ProductRelScriptMapper productRelScriptMapper;


}
