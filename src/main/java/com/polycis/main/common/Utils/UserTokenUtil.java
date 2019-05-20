package com.polycis.main.common.Utils;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.entity.admin.OssAdmin;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 * 用户token工具类
 *
 * @auhtor weitao
 * @create 2019-05-16:01
 */
@Component
public class UserTokenUtil {
    protected static Logger Log = LoggerFactory.getLogger(UserTokenUtil.class);

    /**
     * 从cookie中获取用户信息
     * @param request
     * */
    public OssAdmin getAccountFromToken(HttpServletRequest request,RedisFeignClient redisFeignClient) {
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray == null || cookieArray.length == 0) {
            Log.info("当前请求中未包含任何cookie信息");
            return null;
        }

        //用户token
        String userToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (MainConstants.COOKIE_NAME.equals(cookie.getName())) {
                userToken = cookie.getValue();
            }
        }

        if (StringUtils.isBlank(userToken)) {
            Log.info("获取用户唯一token信息失败！");
            return null;
        }


        //从redis缓存中获取用户信息
        ApiResult apiResult = redisFeignClient.get(userToken);
        if (apiResult.getCode() != CommonCode.SUCCESS.getKey()) {
            Log.info(String.format("根据token:%s,从redis缓存中获取用户信息失败！",userToken));
            return null;
        }

        String userJson = apiResult.getData().toString();
        if(StringUtils.isBlank(userJson)){
            Log.info(String.format("根据用户缓存数据：%s，反序列化用户信息失败！",userJson));
            return null;
        }

        OssAdmin ossAdmin = JSON.parseObject(userJson, new TypeToken<OssAdmin>(){}.getType());
        return ossAdmin;
    }
}
