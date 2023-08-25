package com.dfd.service.handler;

/**
 * @author yy
 * @date 2023/8/25 17:11
 */
public interface RefreshHandler {
    void setNextHandler(RefreshHandler nextHandler);
    void refreshData();
}
