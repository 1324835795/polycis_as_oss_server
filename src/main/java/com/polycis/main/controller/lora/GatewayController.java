package com.polycis.main.controller.lora;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.log.MyLog;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.lora.Gateway;
import com.polycis.main.service.db1.lora.IGatewayService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 网关 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/lora/gateway")
public class GatewayController {

    protected static Logger LOG = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private IGatewayService gatewayService;


    @ApiOperation(value = "查询网关", notes = "查询网关接口")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult getGateway(@RequestBody Gateway gw) {
        ApiResult<Gateway> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            Gateway gwTemp = this.gatewayService.selectById(gw);
            apiResult.setData(gwTemp);
        }catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(e.getMessage());
            LOG.error(String.format("查询网关异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    @MyLog(describe = "添加网关")
    @ApiOperation(value = "添加网关", notes = "添加网关接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult addGateway(@RequestBody Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try{
            List<Gateway> list = this.gatewayService.selectList(new EntityWrapper<Gateway>()
                .eq("name", StringUtils.trimToEmpty(gw.getName()))
                .or()
                .eq("mac", StringUtils.trimToEmpty(gw.getMac())));
            if(!CollectionUtils.isEmpty(list)){
                Set<String> mindSet = new HashSet<>();
                StringBuilder sb = new StringBuilder();
                for(Gateway t : list){
                    if(t.getName().equals(gw.getName()) && !mindSet.contains("name")){
                        mindSet.add("name");
                        sb.append("网关名字已存在 ");
                    }
                    if(t.getMac().equals(gw.getMac()) && !mindSet.contains("mac")){
                        sb.append("网关MAC已存在 ");
                        mindSet.add("mac");
                    }
                    if(mindSet.size() > 1){
                        break;
                    }
                }
                if(sb.length() != 0){
                    apiResult.setMsg(sb.toString());
                    apiResult.setCode(CommonCode.ERROR.getKey());
                    return apiResult;
                }

            }
            apiResult = this.gatewayService.add(gw);
        }catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(e.getMessage());
            LOG.error(String.format("新增网关异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


    @ApiOperation(value = "查看网关列表", notes = "查看网关列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ApiResult list(@RequestBody RequestVO requestVO) {
        ApiResult<Page<Gateway>> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            PageInfoVO pageInfo = requestVO.getPageInfo();
            Integer currentPage = pageInfo.getCurrentPage();
            Integer pageSize = pageInfo.getPageSize();
            Map<String, Object> data = requestVO.getData();
            Gateway gw = JSON.parseObject(JSON.toJSONString(data), Gateway.class);
            Page<Gateway> page = this.gatewayService.findList(currentPage, pageSize, gw);
            apiResult.setData(page);
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(e.getMessage());
            LOG.error(String.format("查看网关列表异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


    @MyLog(describe = "更新网关")
    @ApiOperation(value = "更新网关", notes = "更新网关")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RoleOfAdmin
    public ApiResult update(@RequestBody Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            List<Gateway> list = this.gatewayService.selectList(new EntityWrapper<Gateway>()
                    .eq("name", StringUtils.trimToEmpty(gw.getName()))
                    .or()
                    .eq("mac", StringUtils.trimToEmpty(gw.getMac())));
            if(!CollectionUtils.isEmpty(list)){
                Set<String> mindSet = new HashSet<>();
                StringBuilder sb = new StringBuilder();
                for(Gateway t : list){
                    if(t.getName().equals(gw.getName()) && !mindSet.contains("name")){
                        mindSet.add("name");
                        sb.append("网关名字已存在 ");
                    }
                    if(t.getMac().equals(gw.getMac()) && !mindSet.contains("mac")){
                        sb.append("网关MAC已存在 ");
                        mindSet.add("mac");
                    }
                    if(mindSet.size() > 1){
                        break;
                    }
                }
                if(sb.length() != 0){
                    apiResult.setMsg(sb.toString());
                    apiResult.setCode(CommonCode.ERROR.getKey());
                    return apiResult;
                }

            }
            apiResult = this.gatewayService.updateGateway(gw);
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(e.getMessage());
            LOG.error(String.format("更新网关异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    @RoleOfAdmin
    @MyLog(describe = "删除网关")
    @ApiOperation(value = "删除网关", notes = "删除网关")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiResult delete(@RequestBody Gateway gw) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            apiResult = this.gatewayService.deleteGateway(gw);
        } catch (Exception e){
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(e.getMessage());
            LOG.error(String.format("删除网关异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

}

