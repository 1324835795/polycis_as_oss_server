package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.product.ProductFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.entity.*;
import com.polycis.main.mapper.db1.ProductMapper;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private IDeviceService iDeviceService;

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

    @Override
    public ApiResult<String> addProduct(Product product) {

        //去数据转发层添加设备
        product.setProductEui(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        ApiResult apiResult = productFeignClient.create(product);
        if(apiResult.getCode()== CommonCode.SUCCESS.getKey()){
            //产品添加成功需要入库
            boolean insert = this.insert(product);
            if(insert){
                apiResult.setData(insert);
                return apiResult;
            }
            apiResult.setCode(CommonCode.INSERT_ERROR.getKey());
            apiResult.setMsg(CommonCode.INSERT_ERROR.getValue());
            apiResult.setData(false);
            return apiResult;
        }

        return apiResult;
    }

    @Override
    public ApiResult<String> updateProduct(Product product) {
        //去数据转发层修改设备
        Product product1 = this.selectById(product.getId());
        product.setProductEui(product1.getProductEui());
        ApiResult apiResult = productFeignClient.update(product);
        if(apiResult.getCode()== CommonCode.SUCCESS.getKey()){
            EntityWrapper<Product> wrapper = new EntityWrapper<>();
            boolean insert = this.updateById(product);
            if(insert){
                apiResult.setData(insert);
                return apiResult;
            }
            apiResult.setCode(CommonCode.UPDATE_ERROR.getKey());
            apiResult.setMsg(CommonCode.UPDATE_ERROR.getValue());
            apiResult.setData(false);
            return apiResult;
        }
        return apiResult;
    }

    @Override
    public ApiResult<String> deleteProduct(Product product) {

        ApiResult apiResult = productFeignClient.update(product);
        List<Device> devices = iDeviceService.selectList(new EntityWrapper<Device>()
                .eq("is_delete", 1)
                .eq("product_id", product.getId()));
        if(devices.size()>0){
            apiResult.setMsg("产品下关联有设备,删除失败");
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;
        }

        ApiResult apiResult2 = productFeignClient.delete(product.getProductEui());
        if(apiResult2.getCode()== CommonCode.SUCCESS.getKey()){
            //产品删除成功
            EntityWrapper<Product> wrapper = new EntityWrapper<>();
            boolean b = this.delete(wrapper.eq("product_eui",product.getProductEui()));
            if(b){
                apiResult2.setData(b);
                return apiResult;
            }
            apiResult2.setCode(CommonCode.UPDATE_ERROR.getKey());
            apiResult2.setMsg(CommonCode.UPDATE_ERROR.getValue());
            apiResult2.setData(false);
            return apiResult2;
        }
        return apiResult2;

    }


}
