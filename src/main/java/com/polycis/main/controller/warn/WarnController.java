package com.polycis.main.controller.warn;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.WarnLevel;
import com.polycis.main.service.db3.IDevDataWarnService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
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
    @PostMapping("/warn/warnStatusCal")
    public ApiResult<List<Map<String, Object>>> warnStatusCal(@RequestBody RequestVO param){
        return iwarn.warnStatusCal(param);
    }
    @PostMapping("/warn/createWarnLevel")
    public ApiResult createWarnLevel(@RequestBody WarnLevel warnLevel){
        return iwarn.createWarnLevel(warnLevel);
    }
    @PostMapping("/warn/selectWarnLevel")
    public ApiResult<Map<String,Object>> selectWarnLevel(@RequestBody RequestVO requestVO){
        return iwarn.selectWarnLevel(requestVO);
    }

    /**
     * 删除告警级别配置
     * @param map
     * @return
     */
    @PostMapping("/warn/deleteWarnLevel")
    public ApiResult deleteWarnLevel(@RequestBody Map map){
        return iwarn.deleteWarnLevel(map);
    }

    /**
     * 禁用/启用
     * @param map
     * @return
     */
    @PostMapping("/warn/modifyWarnLevelState")
    public ApiResult modifyWarnLevelState(@RequestBody Map map){
        return iwarn.modifyWarnLevelState(map);
    }
}
