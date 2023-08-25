package com.dfd.task;

import com.dfd.service.FlushTotalSalaryService;
import com.dfd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SaticScheduleTask {

    @Autowired
    private FlushTotalSalaryService flushTotalSalaryService;

    @Autowired
    private UserService userService;

    //3.添加定时任务
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(cron = "0/30 * * * * ?")
    // 每日执行一次，每天0点
    @Scheduled(cron = "0 0 0 * * ?")
    // 每周执行一次，每周一0点
//    @Scheduled(cron = "0 0 0 ? * MON")
    private void configureTasks() {
        log.info("==============================定时任务开始执行==============================");
        long startTime = System.currentTimeMillis();
        flushTotalSalaryService.flushMonthTotalSalary("taskFlush");
        flushTotalSalaryService.flushMonthTotalSalaryItem("taskFlush");
        flushTotalSalaryService.flushMonthTotalSalaryRoom("taskFlush");
        long endTime = System.currentTimeMillis();
        long takeTime = endTime - startTime;
        log.info("==============================定时任务执行结束，耗时：{}毫秒==============================", takeTime);
    }
}
