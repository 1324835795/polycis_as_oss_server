package com.polycis.main.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.entity.Dictionary;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
public interface IDictionaryService extends IService<Dictionary> {

    /**
     * 查询所有字典列表
     * @prarm dictionary
     **/
    Page<Dictionary> selectDicList(Dictionary dictionary, Integer currentPage, Integer pageSize);

    List<Dictionary> selectDownList(Dictionary dictionary);

    Boolean addDictionary(Dictionary dictionary);

    ApiResult<String> deleteDictionary(Dictionary dictionary);

    ApiResult<String> updateDic(Dictionary dictionary);
}
