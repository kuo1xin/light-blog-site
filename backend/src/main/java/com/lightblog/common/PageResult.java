package com.lightblog.common;

import java.util.List;

public class PageResult<T> {
    public List<T> records;
    public long total;
    public int page;
    public int size;

    public PageResult(List<T> records, long total, int page, int size) {
        this.records = records;
        this.total = total;
        this.page = page;
        this.size = size;
    }
}
