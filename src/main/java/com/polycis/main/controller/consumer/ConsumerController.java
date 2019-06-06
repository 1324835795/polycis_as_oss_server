package com.polycis.main.controller.consumer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.log.MyLog;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Org;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IOrgService;
import com.polycis.main.service.db1.IUsersService;
import com.polycis.main.service.db1.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.polycis.main.common.log.MyLog.Operation.ADD;
import static com.polycis.main.common.log.MyLog.Operation.QUERY;

@RestController
@RequestMapping("/consumer")
@Api(value = "ConsumerController", description = "系统模块:客户信息")
public class ConsumerController {
    protected static Logger LOG = LoggerFactory.getLogger(ConsumerController.class);


    @Autowired
    private IOrgService iOrgService;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private RedisFeignClient redisFeignClient;

    @RoleOfAdmin
    @MyLog(describe = "添加客户", operation = ADD)
    @PostMapping("/add")
    @Transactional
    public ApiResult add(@RequestBody Org orgusers) throws IOException {

        ApiResult apiResult1=   iOrgService.addOrgAndUser(orgusers);
        return apiResult1;
    }

    @MyLog(describe = "修改客户名称密码")
    @ApiOperation(value = "修改客户名称密码", notes = "修改客户")
    @PostMapping("/update")
    public ApiResult update(@RequestBody Org orgusers) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 修改的客户的信息两个表一起修改
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                //跟据id修改客户名称
                boolean b = iOrgService.updateById(orgusers);
                if (b) {
                    //跟据登录名修改密码
                    Users user = new Users();
                    user.setLoginname(orgusers.getLoginname());
                    user.setName(orgusers.getName());
                    user.setPassword(orgusers.getPassword());
                    if (orgusers.getStart() != null) {
                        user.setIsStart(orgusers.getStart());
                    }
                    iUsersService.update(user, new EntityWrapper<Users>().eq("loginname", user.getLoginname()));

                }
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("修改客户信息失败");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }


    @ApiOperation(value = "查询客户列表", notes = "查询客户列表")
    @PostMapping("/consumerlist")
    public ApiResult consumerlist(@RequestBody RequestVO requestVO) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        Page<Org> orgsPage = new Page<>(requestVO.getPageInfo().getCurrentPage(), requestVO.getPageInfo().getPageSize());
        EntityWrapper<Org> orgEntityWrapper = new EntityWrapper<>();
        Org org = JSON.parseObject(JSON.toJSONString(requestVO.getData()), Org.class);

        if (null != org.getName() && !"".equals(org.getName())) {
            orgEntityWrapper.like("name", org.getName(), SqlLike.RIGHT);
        }
        orgEntityWrapper.eq("is_delete", 1);
        orgEntityWrapper.orderBy("create_time desc");
        Page<Org> orgPage = iOrgService.selectPage(orgsPage, orgEntityWrapper);
        apiResult.setData(orgPage);
        return apiResult;
    }


    @MyLog(describe = "删除客户")
    @ApiOperation(value = "删除客户", notes = "删除客户")
    @PostMapping("/delete")
    public ApiResult delete(@RequestBody Org orgusers) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 添加的客户只是超级管理客户,不能添加普通客户,需要接入平台自主维护
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                //跟据id修改客户名称
                orgusers.setIsDelete(0);
                boolean b = iOrgService.updateById(orgusers);
                if (b) {
                    Org org = iOrgService.selectById(orgusers.getId());
                    Users user = new Users();
                    user.setLoginname(orgusers.getLoginname());
                    user.setIsDelete(0);
                    iUsersService.update(user, new EntityWrapper<Users>().eq("loginname", user.getLoginname()));
                }
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("客户删除失败");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }


    @ApiOperation(value = "客户下拉框", notes = "下拉框")
    @PostMapping("/downList")
    public ApiResult downList() throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("is_delete", 1);
            List<Org> orgs = iOrgService.selectByMap(param);
            apiResult.setData(orgs);
        } catch (Exception e) {
            apiResult.setMsg("查询失败");
            apiResult.setCode(CommonCode.ERROR.getKey());
            return apiResult;
        }

        return apiResult;
    }

    /**
     * @param param
     * @return
     * @throws IOException
     * @author qiaokai
     */
    @MyLog(operation = QUERY, describe = "查询客户下数量信息")
    @PostMapping("/count")
    public ApiResult count(@RequestBody String param) throws IOException {
        Integer id = (Integer) JSONObject.parseObject(param).get("id");
        ApiResult apiResult = new ApiResult();

        Map<String, Integer> map = iOrgService.selectConsumerCountInfo(id);
        apiResult.setData(map);
        return apiResult;
    }

    /**
     * @param param
     * @return
     * @throws IOException
     * @author qiaokai
     */
    @MyLog(operation = QUERY, describe = "查询客户详情")
    @PostMapping("/info")
    public ApiResult info(@RequestBody String param) throws IOException {

        Integer id = (Integer) JSONObject.parseObject(param).get("id");
        ApiResult apiResult = new ApiResult();

        Org org = iOrgService.selectById(id);

        apiResult.setData(org);

        return apiResult;
    }

    @Autowired
    private ISysLogService iSysLogService;


    @MyLog(operation = QUERY, describe = "查询日志")
    @PostMapping("/log")
    public ApiResult log(@RequestBody RequestVO requestVO) throws IOException {
        ApiResult apiResult = new ApiResult();

        Page<SysLogoPO> page = iSysLogService.selectLogPage(requestVO);
        apiResult.setData(page);
        return apiResult;
    }

}
