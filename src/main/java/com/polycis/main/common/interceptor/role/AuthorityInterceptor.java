package com.polycis.main.common.interceptor.role;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.admin.OssAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;


@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();

        String target = handlerMethod.getBean().getClass().getName() + "." + method.getName();

        RoleOfAdmin roleOfAdmin = AuthorityAnnotationContainer.get(target);

        if(roleOfAdmin != null){

            OssAdmin currentUser = RequestHolder.getCurrentUser();
            if(currentUser.getRole().contains(MainConstants.SYS)){
                return true;
            }else {

                PrintWriter printWriter = response.getWriter();
                ApiResult result = new ApiResult<>();
                result.setCode(CommonCode.AUTH_LIMIT.getKey());
                result.setMsg("用户无权限");
                printWriter.write(result.toString());
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}