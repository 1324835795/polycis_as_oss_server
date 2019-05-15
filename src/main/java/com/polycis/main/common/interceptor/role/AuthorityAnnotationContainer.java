package com.polycis.main.common.interceptor.role;

import java.util.concurrent.ConcurrentHashMap;

public class AuthorityAnnotationContainer {


    public static ConcurrentHashMap<String,Object> ROLE_MAP =new ConcurrentHashMap<String,Object>() ;


    public static void  set(String target, RoleOfAdmin roleOfAdmin){
        ROLE_MAP.put(target,roleOfAdmin);
    }

    public static void  remove(String target){
        ROLE_MAP.remove(target);
    }

    public static RoleOfAdmin  get(String target){
        RoleOfAdmin roleOfAdmin = (RoleOfAdmin) ROLE_MAP.get(target);
        return roleOfAdmin;
    }

}
