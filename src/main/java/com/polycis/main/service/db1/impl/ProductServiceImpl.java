package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Product;
import com.polycis.main.entity.Users;
import com.polycis.main.mapper.db1.ProductMapper;
import com.polycis.main.service.db1.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ProductMapper productMapper;
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

    @Override
    public Page<Product> queryProductListOss(PageInfoVO pageInfo, Product product) {

        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Page<Product> page = new Page<Product>(currentPage, pageSize);

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("pageNumber", (currentPage - 1) * pageSize);

        param.put("pageSize", pageSize);

        if (null != product.getDescription() && !"".equals(product.getDescription())) {
            param.put("query", product.getDescription());
        } else {
            param.put("query", null);
        }

        if (null != product.getId() && !"".equals(product.getId())) {
            param.put("orgId", product.getId());
        } else {
            param.put("orgId", null);
        }

        List<Product> list = productMapper.queryAppList(param);

        Integer count = productMapper.queryAppListCount(param);
        page.setTotal(count);
        page.setRecords(list);
        return page;
    }
}
