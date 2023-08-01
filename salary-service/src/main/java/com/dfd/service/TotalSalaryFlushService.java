package com.dfd.service;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface TotalSalaryFlushService {

    /**
     * 刷新工资汇总表
     */
    void flushTotalSalary();

    /**
     * 刷新工资汇总项目表
     */
    void flushTotalSalaryItem();

    /**
     * 刷新工资清单申报表
     */
    void flushTotalSalaryRoom();
}
