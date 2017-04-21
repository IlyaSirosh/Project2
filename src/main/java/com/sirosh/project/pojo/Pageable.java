package com.sirosh.project.pojo;

import java.io.Serializable;

/**
 * Created by Illya on 4/18/17.
 */
public class Pageable implements Serializable {
    private Integer pageNumber;
    private Integer pageSize;

    public Pageable(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
