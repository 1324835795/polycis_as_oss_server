package com.polycis.main.common.interceptor.role;

import com.polycis.main.common.datasource.DBTypeEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RoleOfAdmin {

}