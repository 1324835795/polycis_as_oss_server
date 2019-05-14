package com.polycis.main.controller.consumer;

import com.polycis.main.controller.user.UsersController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Api(value = "ConsumerController", description = "系统模块:客户信息")
public class ConsumerController {
    protected static Logger LOG = LoggerFactory.getLogger(ConsumerController.class);


}
