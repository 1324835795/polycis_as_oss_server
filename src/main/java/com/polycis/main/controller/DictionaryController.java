package com.polycis.main.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.MainConstants;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.Dictionary;
import com.polycis.main.entity.Product;
import com.polycis.main.entity.Users;
import com.polycis.main.service.db1.IDictionaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}

