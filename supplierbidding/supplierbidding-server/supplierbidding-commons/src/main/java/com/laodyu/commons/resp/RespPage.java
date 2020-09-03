package com.laodyu.commons.resp;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/6
 * @Version 1.0
 **/
public class RespPage {
    private long total;
    private List<?> data;

    public RespPage() {
    }

    public RespPage(long total, List<?> data) {
        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
