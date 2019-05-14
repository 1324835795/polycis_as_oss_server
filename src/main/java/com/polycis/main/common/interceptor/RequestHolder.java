package com.polycis.main.common.interceptor;


import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {
    private static final ThreadLocal<OssAdmin> userHolder = new ThreadLocal<OssAdmin>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(OssAdmin ossAdmin){
        userHolder.set(ossAdmin);
    }

    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }

    public static OssAdmin getCurrentUser(){
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
