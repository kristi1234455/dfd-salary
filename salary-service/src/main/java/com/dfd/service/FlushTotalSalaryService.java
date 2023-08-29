package com.dfd.service;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface FlushTotalSalaryService {

    /**
     * todo:项目一启动，全量刷新
     */


    /**
     * 刷新工资汇总表
     */
    void flushMonthTotalSalary();

    void flushMonthTotalSalary(String currentNumber);

    /**
     * 刷新工资汇总项目表
     */
    void flushMonthTotalSalaryItem();

    void flushMonthTotalSalaryItem(String currentNumber);

    /**
     * 刷新工资清单申报表
     */
    void flushMonthTotalSalaryRoom();

    void flushMonthTotalSalaryRoom(String currentNumber);
}