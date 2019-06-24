package com.polycis.main.controller.script;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.exception.MyException;
import com.polycis.main.common.util.script.FileSizeUtil;
import com.polycis.main.common.util.script.JSEngine;
import com.polycis.main.entity.script.ProductRelScriptBO;
import com.polycis.main.entity.script.ProductRelScriptVO;
import com.polycis.main.service.db1.script.IProductRelScriptService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Js脚本前端控制器
 * </p>
 *
 * @author cg
 * @since 2019-06-19
 */
@RestController
@RequestMapping("/script/js")
public class ProductRelScriptController {
    private static final Logger logger = LoggerFactory.getLogger(ProductRelScriptController.class);

    @Autowired
    private IProductRelScriptService productRelScriptService;

    /**
     * 获取js脚本
     * @param productRelScriptBO
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<ProductRelScriptBO> get(@RequestBody ProductRelScriptBO productRelScriptBO) {
        ApiResult<ProductRelScriptBO> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            ProductRelScriptBO bo = this.productRelScriptService.selectOne(new EntityWrapper<ProductRelScriptBO>()
                .eq("product_id", productRelScriptBO.getProductId()));
            if(null == bo){
                throw new MyException("记录不存在");
            }
            apiResult.setData(bo);
        } catch (MyException me) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(me.getMessage());
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            logger.error(String.format("jsscript get 异常信息：%s", ExceptionUtils.getMessage(e)));
        }
        return apiResult;
    }

    /**
     * 新增js脚本
     * @param productRelScriptBO
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> save(@RequestBody ProductRelScriptBO productRelScriptBO) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            ProductRelScriptBO bo = this.productRelScriptService.selectOne(new EntityWrapper<ProductRelScriptBO>()
                    .eq("product_id", productRelScriptBO.getProductId()));
            if(null != bo){
                throw new MyException("当前产品脚本已存在");
            }
            checkJsFileSize(productRelScriptBO.getScriptContent());
            checkJsFileSize(productRelScriptBO.getScriptDraft());
            this.productRelScriptService.insert(productRelScriptBO);
        } catch (MyException me) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(me.getMessage());
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            logger.error(String.format("jsscript post 异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    /**
     * 更新js脚本
     * @param productRelScriptBO
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> update(@RequestBody ProductRelScriptBO productRelScriptBO) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            checkJsFileSize(productRelScriptBO.getScriptContent());
            checkJsFileSize(productRelScriptBO.getScriptDraft());
            this.productRelScriptService.updateById(productRelScriptBO);
        } catch (MyException me) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(me.getMessage());
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            logger.error(String.format("jsscript put 异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    /**
     * 删除js脚本
     * @param productRelScriptBO
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> delete(@RequestBody ProductRelScriptBO productRelScriptBO) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            this.productRelScriptService.deleteById(productRelScriptBO.getId());
        } catch (MyException me) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(me.getMessage());
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            logger.error(String.format("jsscript delete 异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }

    /**
     * 删除js脚本
     * @param productRelScriptVO
     * @return
     */
    @RequestMapping(value = "/test", method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    public ApiResult<String> test(@RequestBody ProductRelScriptVO productRelScriptVO) {
        ApiResult<String> apiResult = new ApiResult<>(CommonCode.SUCCESS);
        try {
            if(StringUtils.isBlank(productRelScriptVO.getScriptContent())){
                throw new MyException("脚本内容不能为空");
            }
            checkJsFileSize(productRelScriptVO.getScriptContent());
            String result = null;
            if(productRelScriptVO.getOperType().equals(MainConstants.DEVICE_DATA_TYPE_UPLINK)){
                result = JSEngine.rawDataToProtocol(productRelScriptVO.getParam(),false, null, productRelScriptVO.getScriptContent());
            }else if(productRelScriptVO.getOperType().equals(MainConstants.DEVICE_DATA_TYPE_DOWNLINK)){
                result = JSEngine.protocolToRawData(productRelScriptVO.getParam(),false, null, productRelScriptVO.getScriptContent());
            }else{
                throw new MyException("请选择模拟类型");
            }
            apiResult.setData(result);
        } catch (MyException me) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            apiResult.setMsg(me.getMessage());
        } catch (Exception e) {
            apiResult.setCodeMsg(CommonCode.ERROR);
            StackTraceElement[] se = e.getStackTrace();
            if(se[0].getClassName().contains("nashorn")){
                apiResult.setMsg(e.getMessage());
            }
            logger.error(String.format("jsscript test 异常信息：%s", ExceptionUtils.getFullStackTrace(e)));
        }
        return apiResult;
    }


    private void checkJsFileSize(String content) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(content)){
            boolean flag = FileSizeUtil.fileSizeIsOK(content.getBytes("UTF-8").length);
            if(!flag){
                throw new MyException("脚本大小不能超过48KB");
            }
        }
    }

}
