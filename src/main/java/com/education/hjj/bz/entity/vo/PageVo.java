package com.education.hjj.bz.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/9/4.
 */
public class PageVo<T> {

    private Integer total;
    private List<T> dataList;

    public PageVo() {
        this(0, new ArrayList<>());
    }

    public PageVo(Integer total, List<T> dataList) {
        this.total = total;
        this.dataList = dataList;
    }

    public Integer getTotal() {
        return total == null ? 0 : total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
