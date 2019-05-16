package com.polycis.main.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.interceptor.role.RoleOfAdmin;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.admin.IOssAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiaokai
 * @since 2019-05-14
 */
@RestController
@RequestMapping("/ossadmin")
public class OssAdminController {

    protected static Logger LOG = LoggerFactory.getLogger(OssAdminController.class);

    @Autowired
    private RedisFeignClient redisFeignClient;
    @Autowired
    private IOssAdminService iOssAdminService;

    @ApiOperation(value = "oss用户登录", notes = "oss用户登录接口")
    @PostMapping("/login")
    public ApiResult login(@RequestBody Users uss, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin ossAdmin = new OssAdmin();
        ossAdmin.setLoginname(uss.getLoginname());
        ossAdmin.setPassword(uss.getPassword());
        OssAdmin ossAdmin1 = iOssAdminService.isossAdmin(ossAdmin);
        if (ossAdmin1 != null) {

            String key = UUID.randomUUID().toString().substring(0, 16);
            LOG.info("往redis里塞值key:{},value:{}", key, ossAdmin1.toString());
            ApiResult result = redisFeignClient.set(key, JSON.toJSONString(ossAdmin1), MainConstants.TOKEN_LIFETIME);

            if (result.getCode() == CommonCode.SUCCESS.getKey()) {
                Cookie cookie = new Cookie(MainConstants.COOKIE_NAME, key);
                cookie.setMaxAge(MainConstants.COOKIE_LIFETIME);
                cookie.setPath("/");
                cookie.setDomain("");
                // cookie.setHttpOnly(true);
                httpServletResponse.addCookie(cookie);
            } else {
                apiResult.setMsg(CommonCode.ERROR.getValue());
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("redis服务错误,请联系管理员");
                return apiResult;
            }
            apiResult.setData(ossAdmin1);
            return apiResult;
        }
        apiResult.setMsg("用户账号密码错误");
        apiResult.setCode(CommonCode.ERROR.getKey());
        return apiResult;
    }


    @ApiOperation(value = "用户登出", notes = "登录登出")
    @PostMapping("/logout")
    public ApiResult logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ApiResult apiResult = new ApiResult<>();
        Cookie cookie = new Cookie(MainConstants.COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        httpServletResponse.addCookie(cookie);
        return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "用户启用/不启用", notes = "用户启用/不启用")
    @PostMapping("/active")
    public ApiResult active(@RequestBody OssAdmin ossAdmin) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            ossAdmin.setStart(0);
            iOssAdminService.updateById(ossAdmin);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

    @RoleOfAdmin
    @ApiOperation(value = "oss添加用户", notes = "oss添加用户")
    @PostMapping("/add")
    public ApiResult add(@RequestBody OssAdmin ossAdmin) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 添加的用户可以是用户,管理员
            if (ossAdmin.getType() == 1)
                ossAdmin.setRole(MainConstants.USER);
            if (ossAdmin.getType() == 2)
                ossAdmin.setRole(MainConstants.SYS);
            ossAdmin.setStart(1);
            boolean b = false;
            try {
                b = iOssAdminService.insert(ossAdmin);
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("用户登录名已注册");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
    }

    @RoleOfAdmin
    @ApiOperation(value = "删除oss用户", notes = "删除oss用户")
    @PostMapping("/delete")
    public ApiResult delete(@RequestBody OssAdmin ossAdmin) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getId().equals(ossAdmin.getId())) {
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg("不能删除自己");
        }


            ossAdmin.setDel(MainConstants.DELETETED);
            iOssAdminService.updateById(ossAdmin);
            return apiResult;

    }
    @RoleOfAdmin
    @ApiOperation(value = "修改oss用户", notes = "修改oss用户")
    @PostMapping("/update")
    public ApiResult update(@RequestBody OssAdmin ossAdmin) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 操作用户是管理员 且 被操作用户是type=1用户

            boolean b = iOssAdminService.updateById(ossAdmin);
            if (b) {
                return apiResult;
            }
            apiResult.setCode(CommonCode.ERROR.getKey());
            apiResult.setMsg(CommonCode.ERROR.getValue());
            return apiResult;
    }


    @ApiOperation(value = "oss用户列表", notes = "oss用户列表")
    @PostMapping("/list")
    public ApiResult list(@RequestBody RequestVO requestVO) throws IOException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        EntityWrapper<OssAdmin> usersEntityWrapper = new EntityWrapper<>();
        //OssAdmin ossAdmin = JSON.parseObject(JSON.toJSONString(requestVO.getData()), OssAdmin.class);
        /*if (null != ossAdmin.getLoginname() && !"".equals(ossAdmin.getLoginname())) {
            usersEntityWrapper.like("loginname", ossAdmin.getLoginname(), SqlLike.RIGHT);
        }*/
        usersEntityWrapper.eq("del", MainConstants.UN_DELETE);
        usersEntityWrapper.orderBy("create_time desc");
        ApiResult apiResult = new ApiResult<>();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Page<OssAdmin> usersPage = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<OssAdmin> usersPage1 = iOssAdminService.selectPage(usersPage, usersEntityWrapper);
        apiResult.setData(usersPage1);
        return apiResult;
    }


    @ApiOperation(value = "oss用户个人信息", notes = "oss用户个人信息")
    @PostMapping("/selfinfo")
    public ApiResult selfinfo() {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        OssAdmin ossAdmin1 = iOssAdminService.selectById(currentUser);
        apiResult.setData(ossAdmin1);
        return apiResult;
    }


    @ApiOperation(value = "oss用户个人密码修改", notes = "oss用户个人密码修改")
    @PostMapping("/selfpassword")
    public ApiResult selfpassword(@RequestBody OssAdmin ossAdmin) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        boolean b = iOssAdminService.update(ossAdmin, new EntityWrapper<OssAdmin>().eq("id", currentUser.getId()));
        return apiResult;
    }


}

