package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.entity.Dictionary;
import com.polycis.main.mapper.db1.DictionaryMapper;
import com.polycis.main.service.db1.IDictionaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Override
    public  Page<Dictionary> selectDicList(Dictionary dictionary,Integer currentPage,Integer pageSize) {

      
        //查询所有父类
        Page<Dictionary> page = new Page<>(currentPage, pageSize);
        EntityWrapper<Dictionary> dicParentWrapper = new EntityWrapper<>();
        if(dictionary.getName()!=null){
            dicParentWrapper.like("name",dictionary.getName());
        }
        if(dictionary.getParentId()!=null){
            dicParentWrapper.eq("parent_id",dictionary.getId());
        }else{
            dicParentWrapper.eq("parent_id",0);
        }

        dicParentWrapper.orderBy("id",true);
        List<Dictionary> list = this.selectList(dictionary);
        Page<Dictionary> dictionaryPage = this.selectPage(page, dicParentWrapper);
        dictionaryPage.setRecords(list);
        return dictionaryPage;
    }

    @Override
    public List<Dictionary> selectDownList(Dictionary dictionary) {
        List<Dictionary> list = this.selectList(dictionary);
        return list;
    }

    @Override
    public Boolean addDictionary(Dictionary dictionary) {

        if(dictionary.getParentId()==null){
            dictionary.setParentId(0);
        }
        boolean insert = this.insert(dictionary);
        return insert;
    }

    @Override
    public ApiResult<String> deleteDictionary(Dictionary dictionary) {

        ApiResult<String> apiResult = new ApiResult<>();
        if(dictionary.getId()!=null){
            EntityWrapper<Dictionary> dicParentWrapper = new EntityWrapper<>();
            dicParentWrapper.eq("parent_id",dictionary.getId());
            List<Dictionary> parentList = this.selectList(dicParentWrapper);
            if(parentList.isEmpty()){
                boolean b = this.deleteById(dictionary.getId());
                if(!b){
                    apiResult.setMsg(CommonCode.DELETE_ERROR.getValue());
                    apiResult.setCode(CommonCode.DELETE_ERROR.getKey());
                }
               return apiResult;
            }
            apiResult.setMsg("该条目有下级菜单不允许删除");
            apiResult.setCode(CommonCode.DELETE_ERROR.getKey());
            return apiResult;
        }
        apiResult.setMsg(CommonCode.PARAMETER_INVALID.getValue());
        apiResult.setCode(CommonCode.PARAMETER_INVALID.getKey());
        return apiResult;
    }

    @Override
    public ApiResult<String> updateDic(Dictionary dictionary) {

        ApiResult<String> apiResult = new ApiResult<>();
        if(dictionary.getId()!=null){

            boolean b = this.updateById(dictionary);
            if(!b){
                apiResult.setMsg(CommonCode.UPDATE_ERROR.getValue());
                apiResult.setCode(CommonCode.UPDATE_ERROR.getKey());
            }
            return apiResult;
        }
        apiResult.setMsg(CommonCode.PARAMETER_INVALID.getValue());
        apiResult.setCode(CommonCode.PARAMETER_INVALID.getKey());
        return apiResult;
    }

    /**
    * 通用查询接口
    * */
    public List<Dictionary> selectList(Dictionary dictionary) {

        List<Dictionary> list = new ArrayList<>();
        EntityWrapper<Dictionary> dicParentWrapper = new EntityWrapper<>();
        if(dictionary.getName()!=null){
            dicParentWrapper.like("name",dictionary.getName());
        }
        if(dictionary.getParentId()!=null){
            dicParentWrapper.eq("parent_id",dictionary.getId());
        }else{
            dicParentWrapper.eq("parent_id",0);
        }

        dicParentWrapper.orderBy("id",true);
        List<Dictionary> parentList = this.selectList(dicParentWrapper);

        for(int i=0;i<parentList.size();i++){
            Dictionary praent = parentList.get(i);
            EntityWrapper<Dictionary>  dicChildrenWrapper = new EntityWrapper<>();
            dicChildrenWrapper.eq("parent_id",praent.getId());
            List<Dictionary> childrenList = this.selectList(dicChildrenWrapper);
            praent.setChildren(childrenList);
            list.add(praent);
        }
        
        return list;
    }
}
