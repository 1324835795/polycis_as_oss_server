package com.polycis.main.service.db2;

import com.polycis.main.entity.App;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.db2.DevUnionDevice;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.vo.AppVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
public interface IDevUnionDeviceService extends IService<DevUnionDevice> {


    Integer selectOnLineDevice(Users currentUser, List<String> list);

    List<Map<String,Integer>> selectDevonline(App app);

    AppVo selectAppVo(AppVo appVo);
}
