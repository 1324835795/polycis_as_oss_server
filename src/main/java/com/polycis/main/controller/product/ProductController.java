
package com.polycis.main.controller.product;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.Product;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;



/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IDeviceService iDeviceService;


    @ApiOperation(value = "查看产品列表", notes = "查看产品列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ApiResult addApp(@RequestBody RequestVO requestVO) {

        PageInfoVO pageInfo = requestVO.getPageInfo();

        Map<String, Object> data = requestVO.getData();
        Product product = JSON.parseObject(JSON.toJSONString(data), Product.class);
        Page<Product> page = iProductService.queryProductListOss(pageInfo, product);

        page.getRecords().forEach(p -> {
            EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<>();
            deviceEntityWrapper.eq("product_id", p.getId());
            deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
            p.setDevCount(iDeviceService.selectCount(deviceEntityWrapper));
        });
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;

    }

    @ApiOperation(value = "查看单个产品信息", notes = "查看单个产品信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ApiResult info(@RequestBody Product product) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        Product product1 = iProductService.selectById(product);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(product1);
        return apiResult;
    }

    @ApiOperation(value = "查看产品内设备列表", notes = "查看产品设备列表")
    @RequestMapping(value = "/devlist", method = RequestMethod.POST)
    public ApiResult devlist(@RequestBody RequestVO requestVO) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        Page<Device> page = iDeviceService.selectProductDevList(requestVO);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(page);
        return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "增加产品", notes = "增加产品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResult add(@RequestBody Product product) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();

            product.setOrg(currentUser.getOrg());
            iProductService.insert(product);
            apiResult.setSub_code(product.getId());
            return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "修改产品", notes = "修改产品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody Product product) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();

            product.setOrg(currentUser.getOrg());
            iProductService.updateById(product);
            return apiResult;

    }

    @RoleOfAdmin
    @ApiOperation(value = "删除产品", notes = "删除产品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody Product product) {
        ApiResult apiResult = new ApiResult<>();

            List<Device> devices = iDeviceService.selectList(new EntityWrapper<Device>()
                    .eq("is_delete", 1)
                    .eq("product_id", product.getId()));
            if(devices.size()>0){
                apiResult.setMsg("产品下关联有设备,删除失败");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
            product.setIsDelete(MainConstants.DELETETED);
            iProductService.updateById(product);
            return apiResult;

    }


    @ApiOperation(value = "产品下拉列表", notes = "产品下拉列表")
    @RequestMapping(value = "/prolist", method = RequestMethod.POST)
    public ApiResult prolist() {

        EntityWrapper<Product> wrapper = new EntityWrapper<>();

        wrapper.eq("is_delete",MainConstants.UN_DELETE);
        List<Product> products = iProductService.selectList(wrapper);
        ApiResult apiResult = new ApiResult<>();
        apiResult.setData(products);
        return apiResult;
    }

}

