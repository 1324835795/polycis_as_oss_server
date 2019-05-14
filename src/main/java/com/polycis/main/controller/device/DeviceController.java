package com.polycis.main.controller.device;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.device.DevFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.*;
import com.polycis.main.entity.db3.DevDownDataPO;
import com.polycis.main.entity.db3.DevUpDataPO;
import com.polycis.main.entity.vo.DeviceDTO;
import com.polycis.main.service.db1.IAppOrgRelationService;
import com.polycis.main.service.db1.IAppService;
import com.polycis.main.service.db1.IDeviceService;
import com.polycis.main.service.db1.IProductService;
import com.polycis.main.service.db2.IMybatisPlusDB2Service;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/*
*
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-23
*/


@RestController
@RequestMapping("/dev")
public class DeviceController {

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private DevFeignClient devFeignClient;

    @Autowired
    private IAppService iAppService;

    @Autowired
    private IAppOrgRelationService iAppOrgRelationService;

    @Autowired
    private IMybatisPlusDB2Service iMybatisPlusDB2Service;

    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;


    protected static Logger LOG = LoggerFactory.getLogger(DeviceController.class);


    @ApiOperation(value = "设备添加", notes = "设备添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResult add(@RequestBody Device device) {
        Product product = iProductService.selectById(device.getProductId());
        DeviceDTO deviceDTO = new DeviceDTO(device, product);
        // 给传输的包装类设置app_eui,接入层存储应用信息用的是app_eui
        deviceDTO.setAppEui(iAppService.selectById(device.getAppId()).getAppEui());
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS)) {

            // 应用层做了uuid校验,根本上是接入平台要做的,因为是解耦的,接入平台要单独部署,不知道为什么都要放在应用层做
            int i = iDeviceService.selectCount(new EntityWrapper<Device>()
                    .eq("device_uuid", device.getDeviceUuid())
                    .eq("is_delete", MainConstants.UN_DELETE));
            if (i > 0) {
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("设备uuid已存在,请校对设备信息");
                return apiResult;
            }

            LOG.info("设备信息" + deviceDTO.toString());
            ApiResult apiResult1 = devFeignClient.create(deviceDTO);

            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {

                // 自己设置一遍,前端不塞值也没事
                device.setProductId(product.getId());
                // 设置平台类型冗余字段
                device.setPlatform(product.getPlatform());
                if (iDeviceService.insert(device)) {
                    apiResult.setSub_code(device.getId());
                    return apiResult;
                } else {
                    apiResult.setCode(CommonCode.ERROR.getKey());
                    return apiResult;
                }

            }
            return apiResult1;

        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }

    }


    @ApiOperation(value = "设备更新", notes = "设备更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResult update(@RequestBody Device device) {

        // 给传输的包装类设置app_eui,接入层存储应用信息用的是app_eui
        Users currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS)) {

            EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<Device>();
            deviceEntityWrapper.eq("id", device.getId());
            // 设置更新的设备是在app_id 下
            boolean update = iDeviceService.update(device, deviceEntityWrapper);
            if (update) {
                return apiResult;
            }
            apiResult.setMsg(CommonCode.ERROR.getValue());
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;

        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }
    }

    @ApiOperation(value = "设备详情", notes = "设备详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ApiResult info(@RequestBody Device device) {

        // 给传输的包装类设置app_eui,接入层存储应用信息用的是app_eui
        Users currentUser = RequestHolder.getCurrentUser();

        ApiResult apiResult = new ApiResult<>();

        Device device1 = iDeviceService.selectById(device);
        apiResult.setData(device1);
        return apiResult;

    }

    @ApiOperation(value = "设备删除", notes = "设备删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody Device device) {

        // 给传输的包装类设置app_eui,接入层存储应用信息用的是app_eui
        Users currentUser = RequestHolder.getCurrentUser();

        //应该判断这个设备属不属于这个组织
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            // 检查删除的设备是否在该组织下
           Integer i = iAppService.selectDevIsmine(device.getId(),currentUser.getOrg());
           if(i<=0){
               apiResult.setMsg("该设备不在用户所属组织下,想玩火?");
                apiResult.setCode(CommonCode.ERROR.getKey());
               return apiResult;
           }

            ApiResult apiResult1 = devFeignClient.delete(device.getPlatform(), device.getDeviceUuid());
            if (apiResult1.getCode() == CommonCode.SUCCESS.getKey()) {
                EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<Device>();
                deviceEntityWrapper.eq("id", device.getId());
                // 软删除
                device.setIsDelete(MainConstants.DELETETED);
                boolean update = iDeviceService.update(device, deviceEntityWrapper);

                if (update) {
                    return apiResult;
                }
                LOG.info("应用层设备信息删除失败:{}" + device.toString());
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;

            }
            apiResult.setMsg(CommonCode.ERROR.getValue());
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;

        } else {
            apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
            apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
            return apiResult;
        }
    }

    @Autowired
    private IProductService iProductService;

    @ApiOperation(value = "设备模糊查询", notes = "设备模糊查询")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ApiResult list(@RequestBody RequestVO requestVO) {

       /* // 时间有限,code都写在controller了!您瞧好了!
        Users currentUser = RequestHolder.getCurrentUser();



        Map<String, Object> data = requestVO.getData();
        Device device = JSON.parseObject(JSON.toJSONString(data), Device.class);
        // 查询user的app
        List<Integer> list = iAppOrgRelationService.selecctAppId(currentUser.getOrg(),device.getDescription());



        EntityWrapper<Device> deviceEntityWrapper = new EntityWrapper<Device>();

        if (null != device.getDescription() && !"".equals(device.getDescription())) {
            deviceEntityWrapper.andNew().like("name", device.getDescription(), SqlLike.DEFAULT)
                    .or().like("device_uuid", device.getDescription(), SqlLike.DEFAULT);
        }

        if (null != device.getProductId() && !"".equals(device.getProductId())) {


            deviceEntityWrapper.andNew().eq("product_id", device.getProductId());

        }
        if (list.size() == 0) {
            deviceEntityWrapper.in("app_id", "不存在polcis");
        } else {
            deviceEntityWrapper.in("app_id", list);
        }

        deviceEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        deviceEntityWrapper.orderBy("create_time desc");
        Page<Device> devicePage = new Page<>(requestVO.getPageInfo().getCurrentPage(), requestVO.getPageInfo().getPageSize());
        Page<Device> devicePage1 = iDeviceService.selectPage(devicePage, deviceEntityWrapper);
*/
        ApiResult apiResult = new ApiResult<>();
        Page<Device> devicePage1 = iDeviceService.selectDevicePage(requestVO);

        //devicePage1.getRecords().forEach(s -> System.out.println(s.toString()));

        if (null != devicePage1 && !devicePage1.getRecords().isEmpty()) {
            Page<Device> devicePage2 = iMybatisPlusDB2Service.putDevListInfo(devicePage1);
            apiResult.setData(devicePage2);
            return apiResult;
        }
        apiResult.setData(devicePage1);
        return apiResult;
    }


    @ApiOperation(value = "查看设备详情下行数据", notes = "查看设备上行数据")
    @RequestMapping(value = "/downdata", method = RequestMethod.POST)
    public ApiResult downdata(@RequestBody RequestVO requestVO) {

        // 时间有限,code都写在controller了!您瞧好了!
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();

    /*    EntityWrapper<AppOrgRelation> appOrgRelationEntityWrapper = new EntityWrapper<>();
        appOrgRelationEntityWrapper.eq("app_id",device1.getAppId());
        List<AppOrgRelation> appOrgRelations = iAppOrgRelationService.selectList(appOrgRelationEntityWrapper);
*/
        Page<DevDownDataPO> page = iMybatisPlusDB3Service.selectSingleDownData(requestVO);

        apiResult.setData(page);
        return apiResult;
    }

    @ApiOperation(value = "查看设备详情上行数据", notes = "查看设备详情上行数据")
    @RequestMapping(value = "/updata", method = RequestMethod.POST)
    public ApiResult updata(@RequestBody RequestVO requestVO) {

        // 时间有限,code都写在controller了!您瞧好了!
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();


        Page<DevUpDataPO> page = iMybatisPlusDB3Service.selectSingleUpData(requestVO);

        apiResult.setData(page);
        return apiResult;
    }


}

