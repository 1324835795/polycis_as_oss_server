package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Product;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.Users;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IProductService extends IService<Product> {

    Page<Product> queryProductList(Integer currentPage, Integer pageSize, Users currentUser, Product product);
}
