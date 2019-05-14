package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.entity.Product;
import com.polycis.main.entity.Users;
import com.polycis.main.mapper.db1.ProductMapper;
import com.polycis.main.service.db1.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private IProductService iProductService;
    @Override
    public Page<Product> queryProductList(Integer currentPage, Integer pageSize, Users currentUser, Product product) {

        EntityWrapper<Product> wrapper = new EntityWrapper<>();
        if(null!= product.getName() && !"".equals(product.getName())){
            wrapper.like("name",product.getName(), SqlLike.DEFAULT);
        }
        wrapper.eq("org",currentUser.getOrg());
        wrapper.eq("is_delete", MainConstants.UN_DELETE);
        wrapper.orderBy("create_time desc");
        Page<Product> page = new Page<>(currentPage,pageSize);
        Page<Product> productPage = iProductService.selectPage(page, wrapper);
        return productPage;
    }
}
