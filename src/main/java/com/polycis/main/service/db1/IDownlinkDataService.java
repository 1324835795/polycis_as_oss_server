package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.App;
import com.polycis.main.entity.DownlinkData;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.vo.DevDownDataPO;
import com.polycis.main.entity.vo.DeviceQueueItemRequest;

import java.util.List;

/**
 * <p>
 * 设备下行数据表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IDownlinkDataService extends IService<DownlinkData> {

    Integer selectTodayCount(Users currentUser);

    ApiResult downData(Boolean b , DevDownDataPO dq);


    List<DownlinkData> queryDataList(Integer currentPage, Integer pageSize, Users currentUser, Integer productId);

    Integer queryDataCount(Integer productId);
}
