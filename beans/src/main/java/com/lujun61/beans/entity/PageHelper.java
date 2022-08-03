package com.lujun61.beans.entity;

import java.util.List;

/**
 * @description 封装前端分页显示的所需数据
 * @author Jun Lu
 * @date 2022-08-03 08:50:23
 */
public class PageHelper<T> {

    // 总页数
    private int pageCount;

    // 总记录数
    private int count;

    // 分页数据
    private List<T> list;

    public PageHelper() {
    }

    public PageHelper(int pageCount, int count, List<T> list) {
        this.pageCount = pageCount;
        this.count = count;
        this.list = list;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
