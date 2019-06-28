package com.polycis.main.controller.protocollib;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/20
 * description : 描述
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseList<T> {

    public ResponseList(List<T> var1,int var2){
        this.records = var1;
        this.total = var2;
    }
    private List<T> records;
    private int total;
}
