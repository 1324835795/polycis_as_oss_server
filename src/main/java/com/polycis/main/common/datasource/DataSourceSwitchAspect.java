package com.polycis.main.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.polycis.main.common.datasource.DbContextHolder.clearDbType;

/**
 * @Author DGD
 * @date 2018/2/7.
 */

@Component
@Aspect
@Order(-100)
@Slf4j
public class DataSourceSwitchAspect {

    @Pointcut("execution(* com.polycis.main.service.db1..*.*(..))")
    private void db1Aspect() {
    }



    @Pointcut("execution(* com.polycis.main.service.db2..*.*(..))")
    private void db2Aspect() {
    }

    @Pointcut("execution(* com.polycis.main.service.db3..*.*(..))")
    private void db3Aspect() {
    }


    @Before( "db1Aspect()" )
    public void db1(JoinPoint joinPoint) {
        log.info("DataSourceSwitchAspect切换到db1 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db1);
    }

    @Before("db2Aspect()" )
    public void db2 (JoinPoint joinPoint) {
       log.info("DataSourceSwitchAspect切换到db2 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db2);
    }

    @Before("db3Aspect()" )
    public void db3 (JoinPoint joinPoint) {
        log.info("DataSourceSwitchAspect切换到db3 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db3);
    }



    @After( "db1Aspect()" )
    public void db11(JoinPoint joinPoint) {
        log.info("清除数据源db1");
        clearDbType();
    }

    @After("db2Aspect()" )
    public void db22 (JoinPoint joinPoint) {
        log.info("清除数据源db2");
        clearDbType();
    }

    @After("db3Aspect()" )
    public void db33 (JoinPoint joinPoint) {
        log.info("清除数据源db3");
        clearDbType();
    }

    /**
     * 添加注解方式,如果有注解优先注解,没有则按传过来的数据源配置
     * @param joinPoint
     * @param dbTypeEnum
     */
    private void setDataSource(JoinPoint joinPoint, DBTypeEnum dbTypeEnum) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSourceSwitch dataSourceSwitch = methodSignature.getMethod().getAnnotation(DataSourceSwitch.class);
        if (Objects.isNull(dataSourceSwitch) || Objects.isNull(dataSourceSwitch.value())) {
            DbContextHolder.setDbType(dbTypeEnum);
        }else{
            log.info("根据注解来切换数据源,注解值为:"+dataSourceSwitch.value());
            switch (dataSourceSwitch.value().getValue()) {
                case "db1":
                    DbContextHolder.setDbType(DBTypeEnum.db1);
                    break;
                case "db2":
                    DbContextHolder.setDbType(DBTypeEnum.db2);
                    break;
                case "db3":
                    DbContextHolder.setDbType(DBTypeEnum.db3);
                    break;
                default:
                    DbContextHolder.setDbType(dbTypeEnum);
            }
        }
    }
}
