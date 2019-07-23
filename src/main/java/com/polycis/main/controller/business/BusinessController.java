package com.polycis.main.controller.business;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.Product;
import com.polycis.main.mapper.db1.DeviceMapper;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    IDeviceService iDeviceService;
    @Autowired
    IProductService iProductService;
    @Autowired
    DeviceMapper deviceMapper;

    @ApiOperation(value = "设备详情", notes = "设备详情")
    @RequestMapping(value = "/deviceList/{productId}", method = RequestMethod.POST)
    public List<Device> info(@PathVariable(value = "productId") Integer productId) {

        Map<String,Object> map = new HashMap<>();
        map.put("product_id",productId);
        List<Device> devices = iDeviceService.selectByMap(map);
        return devices;
    }

    @ApiOperation(value = "查询产品下所有设备数量", notes = "查询产品下所有设备数量")
    @RequestMapping(value = "/productNum", method = RequestMethod.POST)
    public List<Product> productNum() {

        List<Product> list = new ArrayList<>();
        EntityWrapper<Product> productEntityWrapper = new EntityWrapper<>();
        List<Product> products = iProductService.selectList(productEntityWrapper
                .eq("is_delete", MainConstants.UN_DELETE));

        for(int i=0;i<products.size();i++){
            Product product = products.get(i);
            Integer integer = deviceMapper.selectDevCountBypro(product.getId());
            product.setDevCount(integer);
            list.add(product);
        }
        return list;
    }


}
