
package com.polycis.main.controller.product;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.app.AppFeignClient;
import com.polycis.main.client.product.ProductFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.log.MyLog;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.Dictionary;
import com.polycis.main.entity.Product;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(ProductController.class);

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
    @MyLog(describe = "增加产品")
    @ApiOperation(value = "增加产品", notes = "增加产品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResult add(@RequestBody Product product) {

        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                ApiResult<String>  result = iProductService.addProduct(product);
                //判断是否入库成功
                if(result.getCode() == CommonCode.SUCCESS.getKey()){
                    apiResult.setData(true);
                }else{
                    apiResult.setData(false);
                    apiResult.setCode(result.getCode());
                    apiResult.setMsg(result.getMsg());
                }
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg(e.getMessage());
                apiResult.setCode(CommonCode.ERROR.getKey());
                logger.error(String.format("增加设备异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

    @RoleOfAdmin
    @MyLog(describe = "修改产品")
    @ApiOperation(value = "修改产品", notes = "修改产品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody Product product) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                ApiResult<String>  result = iProductService.updateProduct(product);
                //判断是否修改成功
                if(result.getCode() == CommonCode.SUCCESS.getKey()){
                    apiResult.setData(true);
                }else{
                    apiResult.setData(false);
                    apiResult.setCode(result.getCode());
                    apiResult.setMsg(result.getMsg());
                }
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg(CommonCode.ERROR.getValue());
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;

    }

    @ApiOperation(value = "删除产品信息", notes = "删除字典信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult deleteDictionary(@RequestBody Product product) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                ApiResult<String> result =  iProductService.deleteProduct(product);
                if(result.getCode() == CommonCode.SUCCESS.getKey()){
                    apiResult.setData(true);
                }else{
                    apiResult.setData(false);
                    apiResult.setCode(result.getCode());
                    apiResult.setMsg(result.getMsg());
                }
                //判断是否删除成功
                return apiResult;
            } catch (Exception e) {
                //捕获异常打印异常信息
                apiResult.setData(false);
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg(e.getMessage());
                logger.error(String.format("删除设备异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
            }
        }
        //用户无权限
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
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

