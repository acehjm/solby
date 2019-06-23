package me.solby.xquartz.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;

import java.util.List;

/**
 * Quartz 操作类
 *
 * @author majhdk
 * @date 2019-06-23
 */
public interface QuartzService {

    /**
     * 添加Cron类型的任务
     *
     * @param jobClass       job类
     * @param jobGroupName   job分组名
     * @param cronExpression cron表达式
     */
    void addCronJob(Class<? extends Job> jobClass, String jobGroupName, String cronExpression);

    /**
     * 更新Cron类型的任务
     *
     * @param jobClassName   job类名
     * @param jobGroupName   job分组名
     * @param cronExpression cron表达式
     */
    void updateCronJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * 删除任务
     *
     * @param jobClassName job类名
     * @param jobGroupName job分组名
     */
    void deleteJob(String jobClassName, String jobGroupName);

    /**
     * 暂停任务
     *
     * @param jobClassName job类名
     * @param jobGroupName job分组名
     */
    void pauseJob(String jobClassName, String jobGroupName);

    /**
     * 恢复任务
     *
     * @param jobClassName job类名
     * @param jobGroupName job分组名
     */
    void resumeJob(String jobClassName, String jobGroupName);

    /**
     * 中断任务
     *
     * @param jobClassName job类名
     * @param jobGroupName job分组名
     */
    void interruptJob(String jobClassName, String jobGroupName);

    /**
     * 获取所有正在执行的任务
     *
     * @return
     */
    List<JobExecutionContext> allExecutingJobs();

    /**
     * 获取调度器
     *
     * @return
     */
    Scheduler getScheduler();
}
