package com.polycis.main.common.interceptor;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(value = "test", description = "协议解析器模块:解析器管理")
public class TestController {



    @RequestMapping(value = "/user/login")
    public String test3(HttpServletRequest httpServletRequest) {
        HttpSession sessoin = httpServletRequest.getSession();//这就是session的创建
        sessoin.setAttribute("token","123");//给session添加属性属性name： username,属性 value：TOM

       return "OK";
    }

    @RequestMapping(value = "/user/test")
    public String test4() {

        HttpServletRequest currentRequest = RequestHolder.getCurrentRequest();
        String token = (String)currentRequest.getSession().getAttribute("token");
        System.out.println(token);

        return "经过拦截器测试通过OK";
    }

    @RequestMapping(value = "/user/test2")
    public String test5() {

        HttpServletRequest currentRequest = RequestHolder.getCurrentRequest();
        String token = (String)currentRequest.getSession().getAttribute("token");
        System.out.println(token);
        return token;
    }

}


