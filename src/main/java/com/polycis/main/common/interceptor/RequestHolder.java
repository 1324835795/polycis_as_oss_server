package com.polycis.main.common.interceptor;


import com.polycis.main.entity.Users;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {
    private static final ThreadLocal<Users> userHolder = new ThreadLocal<Users>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(Users sysUser){
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }

    public static Users getCurrentUser(){
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    public static void remove(){
        userHolder.remove();
        requestHolder.remove();
    }

}
