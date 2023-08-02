package com.dfd.service;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface TotalSalaryFlushService {

    /**
     * todo:项目一启动，全量刷新
     */


    /**
     * 刷新工资汇总表
     */
    void flushMonthTotalSalary();

    /**
     * 刷新工资汇总项目表
     */
    void flushMonthTotalSalaryItem();

    /**
     * 刷新工资清单申报表
     */
    void flushMonthTotalSalaryRoom();
}
