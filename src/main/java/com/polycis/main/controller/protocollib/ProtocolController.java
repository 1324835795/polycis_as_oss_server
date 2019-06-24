package com.polycis.main.controller.protocollib;

import com.polycis.main.client.protocollib.ProtocolFeignClient;
import com.polycis.main.client.protocollib.ProtocolInfo;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/19
 * description : 内置协议库控制层
 */
@Slf4j
@RestController
@RequestMapping("/protocol")
public class ProtocolController {
    @Resource
    private ProtocolFeignClient feign;

    @PostMapping("/query")
    public ApiResult query(@RequestBody RequestVO info) {
        ProtocolInfo infos = new ProtocolInfo();
        infos.setPageNo(info.getPageInfo().getCurrentPage());
        infos.setPageSize(info.getPageInfo().getPageSize());
        infos.setQueryParam(String.valueOf(info.getData().get("queryParam")));
        infos.setLanguageType(Integer.valueOf(String.valueOf(info.getData().get("languageType"))));
        return feign.query(infos);
    }

    @PostMapping("/limit")
    public ApiResult limit(@RequestBody ProtocolInfo info) {
        return feign.limit(info);
    }

    @PostMapping("/delete")
    public ApiResult delete(@RequestBody ProtocolInfo info) {
        return feign.delete(info);
    }
}
