package com.polycis.main.common;

import com.polycis.main.service.db1.IIotNetworkDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时处理网络帧数据
 * <p>
 * testTask为spring bean的名称
 * @author Administrator
 */
@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IIotNetworkDataService iIotNetworkDataService;

    public void test(String params) {
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
        Boolean aBoolean = iIotNetworkDataService.rmNetworkdatas(params);
        logger.info("清除网络帧数据开始，状态："+aBoolean);
    }

    public void test2() {
        logger.info("我是不带参数的test2方法，正在被执行");
    }
}
