package com.polycis.main.client.initResource;

import com.polycis.main.common.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther cheng
 */
@Component(value = "LoraInitResourceFeignClient")
@FeignClient(value = "polycis-ns-lora-server", fallback = LoraInitResourceFeignClientFallback.class)
public interface LoraInitResourceFeignClient {


    /**
     * 编写Feign接口简便的方法：把具体需要远程调用的服务（如服务B）中的方法复制过来，去掉实现即可。
     *
     * @param
     * @return
     */


    /** network server 第一个serverID */
    @RequestMapping(value = "${API_V2}/initResource/getInitNetworkId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitNetworkId();

    /** loraServer服务器地址 */
    @RequestMapping(value = "/getInitLoraServerHost", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitLoraServerHost();

    /** lora http请求超时时间     单位s */
    @RequestMapping(value = "/getInitLoraHttpTimeout", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<Integer> getInitLoraHttpTimeout();

    /** lora帐号 */
    @RequestMapping(value = "/getInitUsername", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitUsername();

    @RequestMapping(value = "/getInitUserPassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitUserPassword();

    /** lora 组织 id */
    @RequestMapping(value = "/getInitOrganizationId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitOrganizationId();

    /** lora 默认service_profile_id */
    @RequestMapping(value = "/getInitServiceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInitServiceProfileId();

    /** lora 默认device_profile_id  ABP类型*/
    @RequestMapping(value = "/getInit_ABP_A_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_ABP_A_DeviceProfileId();
    @RequestMapping(value = "/getInit_ABP_B_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_ABP_B_DeviceProfileId();
    @RequestMapping(value = "/getInit_ABP_C_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_ABP_C_DeviceProfileId();

    /** lora 默认device_profile_id  OTAA类型*/
    @RequestMapping(value = "/getInit_OTAA_A_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_OTAA_A_DeviceProfileId();
    @RequestMapping(value = "/getInit_OTAA_B_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_OTAA_B_DeviceProfileId();
    @RequestMapping(value = "/getInit_OTAA_C_DeviceProfileId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getInit_OTAA_C_DeviceProfileId();

    /** app http 推送的地址  */
    @RequestMapping(value = "/getUplinkDataURL", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<String> getUplinkDataURL();

    /** token在redis 中的过期时间 单位 s */
    @RequestMapping(value = "/getTokenTimeout", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ApiResult<Long> getTokenTimeout();
}
