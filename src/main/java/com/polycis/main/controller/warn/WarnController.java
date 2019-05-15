package com.polycis.main.controller.warn;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.service.db3.IDevDataWarnService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/14
 * description : 告警中心
 */
@RestController
public class WarnController {
    @Resource
    private IDevDataWarnService iwarn;

    @PostMapping("/warn/selectWarnInfo")
    public ApiResult<Map<String,Object>> selectWarnInfo(@RequestBody RequestVO param){
        return iwarn.selectWarnInfo(param);
    }
    @PostMapping("/warn/updateWarnRead")
    public ApiResult updateWarnRead(@RequestBody RequestVO param){
        return iwarn.updateWarnRead(param);
    }

}
