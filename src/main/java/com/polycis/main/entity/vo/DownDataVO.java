package com.polycis.main.entity.vo;

import com.polycis.main.entity.DownlinkData;

import java.util.List;

public class DownDataVO {

  private  List<DownlinkData> records;

  private Integer total;

    public List<DownlinkData> getRecords() {
        return records;
    }

    public void setRecords(List<DownlinkData> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
