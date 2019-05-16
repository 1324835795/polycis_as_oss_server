package com.polycis.main.mapper.db1;

import com.polycis.main.entity.Product;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-19
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> queryAppList(Map<String, Object> param);

    Integer queryAppListCount(Map<String, Object> param);
}
