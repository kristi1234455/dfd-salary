package com.dfd.utils;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "分页对象")
public class PageResult<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = -3720998571176536865L;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", name = "records")
    private List<T> records = new ArrayList<>();

    /**
     * 总数据条数
     */
    @ApiModelProperty(value = "总数据条数", name = "totalRecords")
    private long totalRecords;

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数", name = "currentPage")
    private long currentPage;

    /**
     * 当前页最大数据条数
     */
    @ApiModelProperty(value = "当前页最大数据条数", name = "pageSize")
    private long pageSize;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", name = "totalPages")
    private long totalPages;


    /**
     * 构造方法，只用把原来的page类放进来即可
     *
     * @param page 查出来的分页对象
     */
    public PageResult(IPage<T> page) {
        this.records = page.getRecords();
        this.totalRecords = page.getRecords().size();
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalPages = (page.getRecords().size() + page.getSize() - 1) / page.getSize();
    }

    /**
     * 是否有前一页
     *
     * @return boolean
     */
    public boolean hasPrevious() {
        return getCurrentPage() > 0;
    }

    /**
     * 是否有下一页
     *
     * @return boolean
     */
    public boolean hasNext() {
        return getCurrentPage() + 1 < getTotalPages();
    }


    /**
     * 是否有内容
     *
     * @return boolean
     */
    public boolean hasRecords() {
        return getRecords().size() > 0;
    }

    /**
     * 迭代器
     */
    @Override
    public Iterator<T> iterator() {
        return getRecords().iterator();
    }
}

