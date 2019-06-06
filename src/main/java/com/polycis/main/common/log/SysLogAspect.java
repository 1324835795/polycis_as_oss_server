package com.polycis.main.common.log;

import com.alibaba.fastjson.JSON;
import com.polycis.main.common.Utils.UserTokenUtil;
import com.polycis.main.entity.SysLogoPO;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 描述:
 * SysLogAspect
 *
 * @auhtor weitao
 * @create 2019-05-15:06
 */
@Aspect
@Component
public class SysLogAspect {
    protected static Logger Log = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private ISysLogService sysLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.polycis.main.common.log.MyLog)")
    public void logPointCut() {
    }


    //切面 配置通知
    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        SysLogoPO sysLog = new SysLogoPO();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog == null) {
            return;
        }
        String describe = myLog.describe();
        sysLog.setDescribe(describe);


        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        String methodNameUpper = methodName.toUpperCase();
        //自动识别操作类型，方法命名需要标准
        if (methodNameUpper.startsWith(MyLog.Operation.ADD.name())) {
            sysLog.setOperation(MyLog.Operation.ADD.name());
        } else if (methodNameUpper.startsWith(MyLog.Operation.UPDATE.name())) {
            sysLog.setOperation(MyLog.Operation.UPDATE.name());
        } else if (methodNameUpper.startsWith(MyLog.Operation.DELETE.name())) {
            sysLog.setOperation(MyLog.Operation.DELETE.name());
        } else if (methodNameUpper.startsWith(MyLog.Query.FIND.name())
                || methodNameUpper.startsWith(MyLog.Query.GET.name())
                || methodNameUpper.startsWith(MyLog.Query.SELECT.name())
                || methodNameUpper.startsWith(MyLog.Query.QUERY.name())
        ) {
            sysLog.setOperation(MyLog.Operation.QUERY.name());
        } else {
            sysLog.setOperation(myLog.operation().name());
        }
        sysLog.setMethod(className + "." + methodName);


        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = Arrays.toString(args);
        sysLog.setParams(params);

        //获取用request请求上下文
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        //请求ip
        String remoteAddr = request.getRemoteAddr();
        sysLog.setIp(remoteAddr);

        //获取用户信息
        String token = UserTokenUtil.getToken(request);
        OssAdmin ossAdmin = sysLogService.getAccountByToken(token);
        if (ossAdmin != null) {
            sysLog.setUsername(ossAdmin.getLoginname());
            // 1是运营用户
            sysLog.setUserType(1);
        }

        //日志入库
        sysLogService.insertSysLog(sysLog);

        Log.info("操作日志：：" + JSON.toJSONString(sysLog));
    }

}
