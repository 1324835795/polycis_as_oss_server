package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.entity.IotNetworkData;
import com.polycis.main.mapper.db1.IotNetworkDataMapper;
import com.polycis.main.service.db1.IIotNetworkDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-28
 */
@Service
public class IotNetworkDataServiceImpl extends ServiceImpl<IotNetworkDataMapper, IotNetworkData> implements IIotNetworkDataService {

    @Override
    public Page likeDevData(Integer currentPage, Integer pageSize, String dev) {
        Page<IotNetworkData> page = new Page<>(currentPage, pageSize);
        EntityWrapper<IotNetworkData> wrapper = new EntityWrapper<>();
        IotNetworkData netdata = new IotNetworkData();
        wrapper.setEntity(netdata);
        if (dev!= null && null != dev) {
            wrapper.like("topic", dev, SqlLike.DEFAULT );
        }
        wrapper.orderBy("id desc");

        return this.selectPage(page, wrapper);
    }

    @Override
    public Boolean rmNetworkdatas(String params) {
        EntityWrapper<IotNetworkData> wrapper = new EntityWrapper<>();
        IotNetworkData netdata = new IotNetworkData();
        wrapper.setEntity(netdata);
        Date dateBefore = getDateBefore(new Date(), Integer.parseInt(params));
        wrapper.le("create_time",dateBefore);
        return  delete(wrapper);
    }
    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

}
