package com.polycis.main.controller.dictionary;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.common.page.PageInfoVO;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.Dictionary;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.service.db1.IDictionaryService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    protected static Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    @Autowired
    private IDictionaryService iDictionaryService;
    @ApiOperation(value = "查看产品分类信息", notes = "查看产品分类信息")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ApiResult info() {
        ApiResult apiResult = new ApiResult<>();
        EntityWrapper<Dictionary> dictionaryEntityWrapper = new EntityWrapper<>();
        dictionaryEntityWrapper.eq("parent_id", MainConstants.DICTIONARY_PRODUCT);
        List<Dictionary> list = iDictionaryService.selectList(dictionaryEntityWrapper);

        apiResult.setData(list);
        return apiResult;
    }


    @ApiOperation(value = "查看所有字典信息", notes = "查看所有字典信息")
    @RequestMapping(value = "/dictionaryList", method = RequestMethod.POST)
    public ApiResult dictionaryList(@RequestBody RequestVO requestVO) {
        ApiResult apiResult = new ApiResult<>();
        //拿到前端数据 分页信息
        PageInfoVO pageInfo = requestVO.getPageInfo();
        Integer currentPage = pageInfo.getCurrentPage();
        Integer pageSize = pageInfo.getPageSize();
        Map<String, Object> data = requestVO.getData();
        Dictionary dictionary = JSON.parseObject(JSON.toJSONString(data), Dictionary.class);

        Page<Dictionary> dictionaries = iDictionaryService.selectDicList2(dictionary,currentPage,pageSize);
        apiResult.setData(dictionaries);
        return apiResult;
    }


    @ApiOperation(value = "新增字典信息", notes = "查看所有字典信息")
    @RequestMapping(value = "/insertDictionary", method = RequestMethod.POST)
    public ApiResult insertDictionary(@RequestBody Dictionary dictionary) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();

        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                Boolean aBoolean = iDictionaryService.addDictionary(dictionary);
                //判断是否入库成功
                if(!aBoolean){
                    apiResult.setMsg(CommonCode.INSERT_ERROR.getValue());
                    apiResult.setCode(CommonCode.INSERT_ERROR.getKey());
                }
                apiResult.setData(aBoolean);
                return apiResult;
            } catch (Exception e) {
                apiResult.setMsg(CommonCode.ERROR.getValue());
                apiResult.setCode(CommonCode.ERROR.getKey());
                return apiResult;
            }
        }
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }


    @ApiOperation(value = "删除字典信息", notes = "删除字典信息")
    @RequestMapping(value = "/deleteDictionary", method = RequestMethod.POST)
    public ApiResult deleteDictionary(@RequestBody Dictionary dictionary) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                ApiResult<String> result =  iDictionaryService.deleteDictionary(dictionary);
                if(result.getCode() == CommonCode.SUCCESS.getKey()){
                    apiResult.setData(true);
                }else{
                    apiResult.setData(false);
                    apiResult.setCode(result.getCode());
                    apiResult.setMsg(result.getMsg());
                }
                //判断是否删除成功
                return apiResult;
            } catch (Exception e) {
                //捕获异常打印异常信息
                apiResult.setData(false);
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg(e.getMessage());
                logger.error(String.format("删除设备异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
            }
        }
        //用户无权限
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }



    @ApiOperation(value = "修改字典信息", notes = "修改字典信息")
    @RequestMapping(value = "/updateDictionary", method = RequestMethod.POST)
    public ApiResult updateDictionary(@RequestBody Dictionary dictionary) {
        ApiResult apiResult = new ApiResult<>();
        OssAdmin currentUser = RequestHolder.getCurrentUser();
        if (currentUser.getRole().contains(MainConstants.SYS)) {
            try {
                ApiResult<String> result =  iDictionaryService.updateDic(dictionary);
                if(result.getCode() == CommonCode.SUCCESS.getKey()){
                    apiResult.setData(true);
                }else{
                    apiResult.setData(false);
                    apiResult.setCode(result.getCode());
                    apiResult.setMsg(result.getMsg());
                }
                //判断是否删除成功
                return apiResult;
            } catch (Exception e) {
                //捕获异常打印异常信息
                apiResult.setData(false);
                apiResult.setCode(CommonCode.ERROR.getKey());
                apiResult.setMsg(e.getMessage());
                logger.error(String.format("删除设备异常，异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
            }
        }
        //用户无权限
        apiResult.setMsg(CommonCode.AUTH_LIMIT.getValue());
        apiResult.setCode(CommonCode.AUTH_LIMIT.getKey());
        return apiResult;
    }

    @ApiOperation(value = "字典下拉列表", notes = "字典下拉列表")
    @RequestMapping(value = "/dictionaryDown", method = RequestMethod.POST)
    public ApiResult dictionaryDown(@RequestBody Dictionary dictionary) {
        ApiResult apiResult = new ApiResult<>();

        List<Dictionary> list = iDictionaryService.selectDownList(dictionary);
        apiResult.setData(list);
        return apiResult;
    }



    @ApiOperation(value = "字典下拉列表通用", notes = "字典下拉列表")
    @RequestMapping(value = "/dictionaryCommon", method = RequestMethod.POST)
    public ApiResult dictionaryCommon(@RequestBody Dictionary dictionary) {
        ApiResult apiResult = new ApiResult<>();

        List<Dictionary> list = iDictionaryService.selectCommon(dictionary);
        apiResult.setData(list);
        return apiResult;
    }


}

