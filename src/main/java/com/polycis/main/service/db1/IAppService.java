package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.App;
import com.baomidou.mybatisplus.service.IService;
import com.polycis.main.entity.vo.AppVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用表 服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IAppService extends IService<App> {

    Page<App> queryAppList(Integer currentPage, Integer pageSize, App app);

    List<Map<String, Object>> selectProduct(App app);

    void deleteApp(App app);

    boolean addApp(App app);

    List<String> selctTest();

    List<AppVo> seletMonitorApp(Integer org);

    Integer selectDevIsmine(Integer id, Integer org);
}
