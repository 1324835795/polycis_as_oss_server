package com.polycis.main.controller.log;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.service.db3.IlogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
@RestController
public class LogController {
    @Resource
    private IlogService ilogService;

    @PostMapping("/log/sysLog")
    public ApiResult<Map<String,Object>> sysLog(@RequestBody RequestVO requestVO){
        return ilogService.selectSysLogInfo(requestVO);
    }
}
