package com.polycis.main.common.interceptor;


import com.alibaba.fastjson.JSON;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.datasource.DbContextHolder;
import com.polycis.main.entity.Users;
import com.polycis.main.client.redis.RedisFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TokenIntecptor implements HandlerInterceptor {


    private RedisFeignClient redisFeignClient;

    public TokenIntecptor(RedisFeignClient redisFeignClient) {
        this.redisFeignClient = redisFeignClient;
    }

    private static final String START_TIME = "requestStartTime";

    protected static Logger Log = LoggerFactory.getLogger(TokenIntecptor.class);

    /**
     * 处理前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        // 设置跨域
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "content-type");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        if (null != request.getCookies()) {
            Log.info("cookie长度" + request.getCookies().length);
            String token = null;
            for (Cookie cookie : request.getCookies()
                    ) {
                //  System.out.println("cookie name" + cookie.getName() + "cookie value" + cookie.getValue());
                if (MainConstants.COOKIE_NAME.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
            Log.info("token是" + token);
            if (token != null) {
                ApiResult apiResult = redisFeignClient.get(token);
                // 判断redis返回是否能查到数据
                if (apiResult.getCode() == CommonCode.SUCCESS.getKey()) {
                    // 重新设置过期时间
                    redisFeignClient.getandsetexpire(token, MainConstants.TOKEN_LIFETIME);
                    // 将user塞入threadlocal中,方便线程获取user对象
                    String user = apiResult.getData().toString();
                    RequestHolder.add(JSON.parseObject(user, Users.class));

                    return true;
                } else {
                    Log.info("cookie过期,重新登录");
                    PrintWriter printWriter = response.getWriter();
                    ApiResult result = new ApiResult<>();
                    result.setCode(CommonCode.TOKEN_INVALID.getKey());
                    result.setMsg("请重新登录");
                    printWriter.write(result.toString());
                    return false;
                }
            }
        } else {
            PrintWriter printWriter = response.getWriter();
            Log.info("未携带cookie,重新登录");
            ApiResult result = new ApiResult<>();
            result.setCode(CommonCode.TOKEN_INVALID.getKey());
            result.setMsg("请重新登录");
            printWriter.write(result.toString());
            return false;
        }
        PrintWriter printWriter = response.getWriter();
        Log.info("未携带cookie,重新登录");
        ApiResult result = new ApiResult<>();
        result.setCode(CommonCode.TOKEN_INVALID.getKey());
        result.setMsg("请重新登录");
        printWriter.write(result.toString());
        return false;
    }


    /**
     * 处理后调用（正常）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 处理后调用(任何情况)
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 清除threadlocal存储信息,防止内存泄漏
        removeThreadLocalInfo();
    }

    /**
     * 移除信息,包括数据源类型,与user信息
     */
    public void removeThreadLocalInfo() {
        DbContextHolder.clearDbType();
        RequestHolder.remove();
    }
}