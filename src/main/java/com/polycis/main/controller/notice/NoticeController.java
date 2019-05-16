package com.polycis.main.controller.notice;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Notice;
import com.polycis.main.service.db1.INoticeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 公告模块
 */
@RestController
public class NoticeController {
    @Resource
    private INoticeService inotice;

    @PostMapping("/notice/createNoticeMsg")
    public ApiResult createNoticeMsg(@RequestBody Notice notice){
        return inotice.createNoticeMsg(notice);
    }
    @PostMapping("/notice/selectNoticeMsg")
    public ApiResult<Map<String,Object>> selectNoticeMsg(@RequestBody RequestVO requestVO){
        return inotice.selectNoticeMsg(requestVO);
    }
    @PostMapping("/notice/updateInfoDead")
    public ApiResult updateInfoDead(@RequestBody Map map){
        return inotice.updateInfoDead(map);
    }
}
