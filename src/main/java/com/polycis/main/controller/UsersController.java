package com.polycis.main.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Users;
import com.polycis.main.service.db1.IUsersService;
import com.polycis.main.client.redis.RedisFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author qiakai
 * @since 19/4/19
 */
@RestController
@RequestMapping("/user")
@Api(value = "UsersController", description = "系统模块:用户信息")
public class UsersController {
    protected static Logger LOG = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private IUsersService iUsersService;

    @Autowired
    private RedisFeignClient redisFeignClient;


    /*@ApiOperation(value = "设置用户头像", notes = "设置当前用户头像")
    @PostMapping("/profiles")
    public String setUserProfile(@RequestParam(required = true) MultipartFile profile) {
        return iUsersService.updUserProfile(profile);
    }
*/
   /* @ApiOperation(value = "用户登录", notes = "登录接口")
    @GetMapping("/login2")
    public ApiResult login(@RequestParam String loginname, @RequestParam String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        ApiResult apiResult = new ApiResult<>();
        Users us = new Users();
       *//* us.setLoginname(uss.getLoginname());
        us.setPassword(uss.getPassword());*//*

        us.setLoginname(loginname);
        us.setPassword(password);
        Users userss = iUsersService.isUser(us);
        if (userss != null) {

            String key = UUID.randomUUID().toString().substring(0, 16);
            LOG.info("往redis里塞值key:{},value:{}", key, userss.toString());
            ApiResult result = redisFeignClient.set(key, JSON.toJSONString(userss), MainConstants.TOKEN_LIFETIME);

            if (result.getCode() == CommonCode.SUCCESS.getKey()) {
                Cookie cookie = new Cookie(MainConstants.COOKIE_NAME, key);
                cookie.setMaxAge(MainConstants.COOKIE_LIFETIME);
                cookie.setPath("/");
                cookie.setDomain("");
                cookie.setHttpOnly(true);
                httpServletResponse.addCookie(cookie);
            } else {
                apiResult.setMsg(CommonCode.ERROR.getValue());
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("redis服务错误,请联系管理员");
                return apiResult;
            }
            apiResult.setData(userss);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.ERROR.getValue());
        apiResult.setCode(CommonCode.ERROR.getKey());
        apiResult.setMsg("密码错误");
        return apiResult;
    }*/


    @ApiOperation(value = "用户登录", notes = "登录接口")
    @PostMapping("/login")
    public ApiResult login(@RequestBody Users uss, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        ApiResult apiResult = new ApiResult<>();
        Users us = new Users();
        us.setLoginname(uss.getLoginname());
        us.setPassword(uss.getPassword());
        Users userss = iUsersService.isUser(uss);
        if (userss != null) {

            String key = UUID.randomUUID().toString().substring(0, 16);
            LOG.info("往redis里塞值key:{},value:{}", key, userss.toString());
            ApiResult result = redisFeignClient.set(key, JSON.toJSONString(userss), MainConstants.TOKEN_LIFETIME);

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
            apiResult.setData(userss);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.ERROR.getValue());
        apiResult.setCode(CommonCode.ERROR.getKey());
        apiResult.setMsg("错误");
        return apiResult;
    }


    @ApiOperation(value = "用户登出", notes = "登录登出")
    @PostMapping("/logout")
    public ApiResult logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        ApiResult apiResult = new ApiResult<>();
        Cookie cookie = new Cookie(MainConstants.COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        httpServletResponse.addCookie(cookie);
        return apiResult;
    }

    @ApiOperation(value = "用户启用/不启用", notes = "用户启用/不启用")
    @PostMapping("/active")
    public ApiResult active(@RequestBody Users users) throws IOException {
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        if (currentUser.getRole().contains(MainConstants.SYS) && currentUser.getOrg().equals(users.getOrg())) {
            users.setIsStart(0);
            iUsersService.updateById(users);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping("/add")
    public ApiResult add(@RequestBody Users users) throws IOException {
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 添加的用户只是用户,不能管理员添加管理员
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            users.setOrg(currentUser.getOrg());
            if(users.getType()==1){
                users.setRole(MainConstants.USER);
            }else {
                users.setRole(MainConstants.SYS);
            }
            users.setIsStart(1);

            boolean b = false;
            try {
                b = iUsersService.insert(users);
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg("用户登录名重复");
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }

        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }


    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("/delete")
    public ApiResult delete(@RequestBody Users users) throws IOException {
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 操作用户是管理员 且 被操作用户是type=1用户
        Users users1 = iUsersService.selectById(users);
        if (currentUser.getRole().contains(MainConstants.SUSYS)) {
            users1.setIsDelete(MainConstants.DELETETED);
            iUsersService.updateById(users1);
            return apiResult;
        }
        if (currentUser.getRole().contains(MainConstants.SYS) && currentUser.getOrg().equals(users1.getOrg()) && users1.getType()==1 ) {
            users1.setIsDelete(MainConstants.DELETETED);
            iUsersService.updateById(users1);
            return apiResult;
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PostMapping("/update")
    public ApiResult update(@RequestBody Users users) throws IOException {
        Users currentUser = RequestHolder.getCurrentUser();
        ApiResult apiResult = new ApiResult<>();
        // 操作用户是管理员 且 被操作用户是type=1用户
        if (currentUser.getRole().contains(MainConstants.SYS)   ) {
            users.setOrg(currentUser.getOrg());
            boolean b = iUsersService.updateById(users);
            if (b) {
                return apiResult;
            }
            apiResult.setMsg(CommonCode.ERROR.getValue());
            return apiResult;
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }


    @ApiOperation(value = "用户列表", notes = "用户列表")
    @PostMapping("/list")
    public ApiResult list(@RequestBody RequestVO requestVO) throws IOException {
        Users currentUser = RequestHolder.getCurrentUser();
        EntityWrapper<Users> usersEntityWrapper = new EntityWrapper<>();
        Users user = JSON.parseObject(JSON.toJSONString(requestVO.getData()), Users.class);
        if (null != user.getLoginname() && !"".equals(user.getLoginname())) {
            usersEntityWrapper.like("loginname", user.getLoginname(), SqlLike.RIGHT);
        }
        usersEntityWrapper.eq("org", currentUser.getOrg());
        usersEntityWrapper.eq("is_delete", MainConstants.UN_DELETE);
        usersEntityWrapper.orderBy("create_time desc");
        ApiResult apiResult = new ApiResult<>();
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Page<Users> usersPage = new Page<>(pageInfo.getCurrentPage(), pageInfo.getPageSize());
        Page<Users> usersPage1 = iUsersService.selectPage(usersPage, usersEntityWrapper);
        apiResult.setData(usersPage1);

        return apiResult;
    }





  /*  @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息接口")
    @PostMapping(value = "/queryuser")
    public R listUsers(@RequestBody RequestVO requestVO) {

        PageInfoVO pageInfoVO =requestVO.getPageInfo();
        Integer currentPage = pageInfoVO.getCurrentPage();
        Integer pageSize = pageInfoVO.getPageSize();
        Map<String, Object> data = requestVO.getData();
        Integer userId = (Integer) data.get("userId");
        Map<String, Object> params = (Map<String, Object>) data.get("appInfo");
        Users uss = JSON.parseObject(JSON.toJSONString(params), Users.class);

        Users users = iUsersService.seltUserBybiInRedis(userId);
        String role = users.getRole();
        if(uss.getOrg()==null) {
            uss.setOrg(users.getOrg());
        }
        if (!role.isEmpty() && role.contains(ROLE_SYS)) {
            LOG.info("接收参数" + uss.toString());
            Page<Users> usersPage = iUsersService.listuserByPage(currentPage, pageSize, uss);
            LOG.info(usersPage.toString());
            return R.ok().put("page", usersPage);
        }else {
            return  rrExceptionHandler.handleAuthorizationException(new AuthorizationException());
        }


    }*/


/*
    @ApiOperation(value = "用户详情信息", notes = "查询用户详细信息接口")
    @PostMapping(value = "/finduser")
    public R queryUsersByid(@RequestBody RequestVO requestVO) {
        Map<String, Object> data = requestVO.getData();
        Integer userId = (Integer) data.get("userId");
        Users users = iUsersService.seltUserBybiInRedis(userId);
        LOG.info(users.toString());
        return R.ok().put("user", users);
    }*/




   /* @ApiOperation(value = "更新用户信息", notes = "更新用户信息接口")
    @PostMapping(value = "/updateUsers")
    public R updateUsers(@RequestBody RequestVO requestVO) {
        Map<String, Object> data = requestVO.getData();
        Integer userId = (Integer) data.get("userId");
        Map<String, Object> params = (Map<String, Object>) data.get("users");
        Users user = JSON.parseObject(JSON.toJSONString(params), Users.class);

        Users users = iUsersService.seltUserBybiInRedis(userId);
        String role = users.getRole();
        if (!role.isEmpty() && role.contains(ROLE_SYS)) {
            user.setUpdateTime(new Date());
            boolean b = iUsersService.updateById(user);
            if (b) {
                //更新缓存
                redisUtils.set(String.valueOf(users.getId()),users,NOT_EXPIRE);
                return R.ok();
            } else {
                return R.error(500, "操作数据库出错");
            }
        } else {
            return  rrExceptionHandler.handleAuthorizationException(new AuthorizationException());
        }
    }

*/


  /*  @ApiOperation(value = "删除用户", notes = "删除用户接口")
    @PostMapping(value = "/deleteUsers")
    public R deleteUsers(@RequestBody RequestVO requestVO) {
        Map<String, Object> data = requestVO.getData();
        Integer userId = (Integer) data.get("userId");
        Map<String, Object> params = (Map<String, Object>) data.get("users");
        Users user = JSON.parseObject(JSON.toJSONString(params), Users.class);

        Users users = iUsersService.seltUserBybiInRedis(userId);
        String role = users.getRole();
        if (!role.isEmpty() && role.contains(ROLE_SYS)) {
            Users users1 = iUsersService.selectById(user.getId());
           if(users1.getRole().contains(ROLE_SUSYS)) {
               return R.error(500, "删除超管,要自尽吗");
           }else {
               user.setIsDelete(0);
               boolean b = iUsersService.updateById(user);
               //清理缓存
               redisUtils.delete(String.valueOf(users.getId()));
               if (b) {
                   return R.ok();
               } else {
                   return R.error(500, "操作数据库出错");
               }
           }
        } else {
            // throw new Exception("尊敬的用户您非超级管理员,无权限管理组织");
            return  rrExceptionHandler.handleAuthorizationException(new AuthorizationException());
        }
    }*/

   /* @ApiOperation(value = "添加用户", notes = "添加用户接口")
    @PostMapping(value = "/addUsers")
    public R addUsers(@RequestBody Users user) {
        Map<String, Object> data = requestVO.getData();
        Integer userId = (Integer) data.get("userId");
        Map<String, Object> params = (Map<String, Object>) data.get("users");
        Users user = JSON.parseObject(JSON.toJSONString(params), Users.class);
        return null;
    }*/

/*

    @ApiOperation(value = "登录用户名唯一校验", notes = "登录用户名唯一校验接口")
    @PostMapping(value = "/checkLoginNameUnique")
    public R checkLoginNameUnique(@RequestBody  Map<String, Object> param ) {

        String loginName = String.valueOf(param.get("loginName"));

        Boolean b = iUsersService.checkLoginNameUnique(loginName);
      if(b){
          return R.ok();
      }else{
          return  R.error(500,"用户名已存在");
      }
    }
*/


}

