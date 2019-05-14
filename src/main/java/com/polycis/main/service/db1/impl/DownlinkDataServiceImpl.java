package com.polycis.main.service.db1.impl;

import com.polycis.main.client.downdata.DownDataClient;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.DownlinkData;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.vo.DevDownDataPO;
import com.polycis.main.mapper.db1.DownlinkDataMapper;
import com.polycis.main.service.db1.IDownlinkDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.polycis.main.common.util.UTilsByte.bytes2hex03;
import static com.polycis.main.common.util.UTilsByte.toByteArray;
import static com.polycis.main.common.util.aes.AESkey.decryptBASE64;
import static com.polycis.main.common.util.aes.AESkey.encryptBASE64;

/**
 * <p>
 * 设备下行数据表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class DownlinkDataServiceImpl extends ServiceImpl<DownlinkDataMapper, DownlinkData> implements IDownlinkDataService {

    protected static Logger Log = LoggerFactory.getLogger(DownlinkDataServiceImpl.class);
    @Autowired
    private DownDataClient appFeignClient;
    @Autowired
    private DownlinkDataMapper downlinkDataMapper;


    @Override
    public Integer selectTodayCount(Users currentUser) {
        return downlinkDataMapper.selectTodayCount(currentUser.getOrg());
    }

    @Override
    public ApiResult downData(Boolean b, DevDownDataPO dq) {

        ApiResult apiResult = new ApiResult<>();
        String info = dq.getDownData();
        //未加密
        if(!b){
            try {
               // System.out.println("为普通16进制需要加密");
                byte[] bytes = toByteArray(info);
                String base64data = encryptBASE64(bytes);
                Log.info("----------" + base64data);
                Log.info("++++++++++" + bytes.toString());
                dq.setDownData(info);
                dq.setEncodeData(base64data);

                ApiResult result = appFeignClient.downData(dq);

                if (result.getCode() == CommonCode.SUCCESS.getKey()) {
                    Log.info("设备下行数据发送成功");
                    DownlinkData downData = new DownlinkData();
                    downData.setData(info);
                    downData.setInfo(base64data);
                    downData.setCreateTime(new Date());
                    downData.setStatus(1);
                    if(dq.getDeviceUuid()==null){
                        for(int i =0;i<dq.getDevUuidList().size();i++){
                            downData.setDeviceId(dq.getDevUuidList().get(i));
                            this.insert(downData);
                        }
                    }else{
                        downData.setDeviceId(dq.getDeviceUuid());
                        this.insert(downData);
                    }
                    return apiResult;
                }

            } catch (Exception e) {
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("发送下行数据失败");
                Log.error("下行数据异常,异常信息:%s", ExceptionUtils.getFullStackTrace(e));
            }
        }
        //加密
        else if(b){
            try {
                // System.out.println("为base64加密内容需要解密");
                byte[] bytes = decryptBASE64(info);
                String base64data = bytes2hex03(bytes);
                Log.info("----------" + base64data);
                Log.info("++++++++++" + bytes.toString());
                dq.setDownData(base64data);
                dq.setEncodeData(info);
                ApiResult result = appFeignClient.downData(dq);
                if (result.getCode() == CommonCode.SUCCESS.getKey()) {
                    Log.info("设备下行数据发送成功");
                    DownlinkData downData = new DownlinkData();
                    downData.setData(base64data);
                    downData.setInfo(info);
                    downData.setCreateTime(new Date());
                    downData.setStatus(1);
                    if(dq.getDeviceUuid()==null){
                        for(int i =0;i<dq.getDevUuidList().size();i++){
                            downData.setDeviceId(dq.getDevUuidList().get(i));
                            this.insert(downData);
                        }
                    }else{
                        downData.setDeviceId(dq.getDeviceUuid());
                        this.insert(downData);
                    }
                    return apiResult;
                }

            } catch (Exception e) {
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg("发送下行数据失败");
                Log.error(String.format("下行数据异常,异常信息:%s", ExceptionUtils.getFullStackTrace(e)));
            }

        }
        apiResult.setMsg(CommonCode.ERROR.getValue());
        apiResult.setCode(CommonCode.ERROR.getKey());
        return apiResult;
    }

    @Override
    public List<DownlinkData> queryDataList(Integer currentPage, Integer pageSize, Users currentUser, Integer productId ) {

        List<DownlinkData> downlinkData = downlinkDataMapper.selectDownData(productId, currentPage - 1, pageSize);

        return downlinkData;
    }

    @Override
    public Integer queryDataCount(Integer productId) {

        Integer integer = downlinkDataMapper.selectDownDataCount(productId);
        return integer;
    }


}
