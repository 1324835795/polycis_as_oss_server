/*
package com.polycis.main.common.annotation;

import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.admin.OssAdmin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAspect {
    //定义切点
    @Pointcut("@annotation(com.polycis.main.common.annotation.RoleOfAdmin)")
    public void doAspect() {
    }

    @Before("doAspect()")
    public void doBefore(JoinPoint joinPoint) throws RoleException {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
       if(!currentUser.getRole().contains(MainConstants.SYS)){
          throw new RoleException("无权限",40001);
       }
    }



    */
/*

    @After("doAspect()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("After......");
    }



    @AfterReturning("doAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
        log.info("AfterReturning......");
    }

    @AfterThrowing("doAspect()")
    public void ddd() {
        log.info("AfterThrowing......");
    }
*//*


}*/
