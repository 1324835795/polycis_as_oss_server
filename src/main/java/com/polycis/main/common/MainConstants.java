package com.polycis.main.common;

public class MainConstants {


    public static final String USER ="user";
    public static final String SYS ="sys";
    public static final String SUSYS ="susys";

    // cookie过期时间
    // 在浏览器中查看,发现时间小了7小时..不知道问题原因
    public static final int COOKIE_LIFETIME =36000;

    // redis里token令牌过期时间
    public static final int TOKEN_LIFETIME =7200;

    // is_delete 字段 未删除
    public static final int UN_DELETE =1;

    // is_delete字段 已删除
    public static final int DELETETED =0;


    /**
     * cookie名
     */
    public static String COOKIE_NAME="cncompolycistokenoss";


    public static final Integer HTTP_PUSH=1;

    // 字典表 产品分类的父id
    public static final Integer DICTIONARY_PRODUCT=1;





}
