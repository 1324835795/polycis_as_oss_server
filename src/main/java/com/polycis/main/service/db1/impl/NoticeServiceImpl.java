package com.polycis.main.service.db1.impl;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Notice;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.mapper.db1.NoticeMapper;
import com.polycis.main.service.db1.INoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 描述
 */
@Service
public class NoticeServiceImpl implements INoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public ApiResult createNoticeMsg(Notice notice) {
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        notice.setUserId(currentUser.getId());
        noticeMapper.insertNotice(notice);
        ApiResult apiResult = new ApiResult(CommonCode.SUCCESS,0,null);
        apiResult.setMsg("发布成功");
        return apiResult;
    }

    @Override
    public ApiResult<Map<String,Object>> selectNoticeMsg(RequestVO requestVO) {
        List<Notice> records = noticeMapper.selectNotice(requestVO);
        int total = noticeMapper.selectNoticeCount(requestVO);
        Map<String,Object> map = new HashMap<>(16);
        map.put("records",records);
        map.put("total",total);
        ApiResult<Map<String,Object>> apiResult = new ApiResult<>(CommonCode.SUCCESS,0,null);
        apiResult.setData(map);
        return apiResult;
    }

    @Override
    public ApiResult updateInfoDead(Map map) {
        noticeMapper.updateDeadTime(map);
        return new ApiResult(CommonCode.SUCCESS);
    }
}
