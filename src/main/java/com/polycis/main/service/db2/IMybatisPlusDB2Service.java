package com.polycis.main.service.db2;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.App;
import com.polycis.main.entity.Device;
import com.polycis.main.entity.vo.AppVo;

import java.util.List;
import java.util.Map;

public interface IMybatisPlusDB2Service {
    App appInfo(App app);

    List<Map<String,Integer>> selectDevonline(App app);


    Page<Device> putDevListInfo(Page<Device> devicePage1);

    AppVo selectAppVo(AppVo appVo);

}
