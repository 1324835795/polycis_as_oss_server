package com.polycis.main.controller.consumer;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Org;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IOrgService;
import com.polycis.main.service.db1.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "添加客户", notes = "添加客户")
    @PostMapping("/add")
    public ApiResult add(@RequestBody Org orgusers) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 添加的客户只是超级管理客户,不能添加普通客户,需要接入平台自主维护
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {

                boolean b = iOrgService.insert(orgusers);
                //查询刚刚的组织
                Map<String,Object> param = new HashMap<>();
                param.put("loginname",orgusers.getLoginname());
                List<Org> orgs = iOrgService.selectByMap(param);
                //自增id为组织id
                Integer id = orgs.get(0).getId();
                if(b){
                    //创建组织成功 继续创建客户
                    Users user = new Users();
                    user.setOrg(id);
                    user.setLoginname(orgusers.getLoginname());
                    user.setRole("susys");
                    user.setType(3);
                    user.setName(orgusers.getName());
                    user.setPassword(orgusers.getPassword());
                    user.setIsStart(1);
                    iUsersService.insert(user);
                }
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("客户登录名已被注册");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

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
                if(b){
                    //跟据登录名修改密码
                    Users user = new Users();
                    user.setLoginname(orgusers.getLoginname());
                    user.setName(orgusers.getName());
                    user.setPassword(orgusers.getPassword());
                    if(orgusers.getStart()!=null){
                        user.setIsStart(orgusers.getStart());
                    }
                    iUsersService.update(user,new EntityWrapper<Users>().eq("loginname", user.getLoginname()));

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
        orgEntityWrapper.eq("is_delete",1);
        orgEntityWrapper.orderBy("create_time desc");
        Page<Org> orgPage = iOrgService.selectPage(orgsPage, orgEntityWrapper);
        apiResult.setData(orgPage);
        return apiResult;
    }


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
                if(b){
                    Org org = iOrgService.selectById(orgusers.getId());
                    Users user = new Users();
                    user.setLoginname(orgusers.getLoginname());
                    user.setIsDelete(0);
                    iUsersService.update(user,new EntityWrapper<Users>().eq("loginname", user.getLoginname()));
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

    //客户下拉框
    @ApiOperation(value = "删除客户", notes = "删除客户")
    @PostMapping("/downList")
    public ApiResult downList(@RequestBody Org orgusers) throws IOException{
        ApiResult apiResult = new ApiResult<>();

        return apiResult;
    }

}
