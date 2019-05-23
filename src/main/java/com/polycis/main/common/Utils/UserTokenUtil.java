package com.polycis.main.common.Utils;

import com.polycis.main.common.MainConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static String getToken(HttpServletRequest request) {
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
        return userToken;
    }
}
