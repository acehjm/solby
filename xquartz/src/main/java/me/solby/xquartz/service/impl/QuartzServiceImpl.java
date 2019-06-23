package me.solby.xquartz.service.impl;

import me.solby.xquartz.exception.QuartzException;
import me.solby.xquartz.service.QuartzService;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务工具类
 *
 * @author majhdk
 * @date 2019-06-22
 */
@Component
public class QuartzServiceImpl implements QuartzService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addCronJob(Class<? extends Job> jobClass, String jobGroupName, String cronExpression) {
        var jobKey = JobKey.jobKey(jobClass.getSimpleName(), jobGroupName);
        var triggerKey = TriggerKey.triggerKey(jobClass.getSimpleName(), jobGroupName);
        try {
            if (scheduler.checkExists(jobKey)) {
                throw new QuartzException("定时任务已存在");
            }
            if (scheduler.checkExists(triggerKey)) {
                throw new QuartzException("触发器已存在");
            }

            // 构建job信息
            var jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobKey)
                    .build();

            // 表达式调度构建器(即任务执行的时间)
            var scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            // 按新的cronExpression表达式构建一个新的trigger
            var trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("创建定时任务失败");
            throw new QuartzException("创建定时任务失败", e);
        }
    }

    @Override
    public void updateCronJob(String jobClassName, String jobGroupName, String cronExpression) {
        var triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        try {
            if (!scheduler.checkExists(triggerKey)) {
                throw new QuartzException("触发器不存在");
            }

            // 表达式调度构建器
            var scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            // 获取触发器
            var trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            logger.error("更新定时任务失败");
            throw new QuartzException("更新定时任务失败", e);
        }
    }

    @Override
    public void deleteJob(String jobClassName, String jobGroupName) {
        try {
            // 暂停触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            // 解除调度器和任务关系
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败");
            throw new QuartzException("删除定时任务失败", e);
        }
    }

    @Override
    public void pauseJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("暂停任务失败");
            throw new QuartzException("暂停任务失败", e);
        }
    }

    @Override
    public void interruptJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.interrupt(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("中断任务失败");
            throw new QuartzException("中断任务失败", e);
        }
    }

    @Override
    public void resumeJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("恢复任务失败");
            throw new QuartzException("恢复任务失败", e);
        }
    }

    @Override
    public List<JobExecutionContext> allExecutingJobs() {
        try {
            return scheduler.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            logger.error("获取当前正在执行的任务失败");
            throw new QuartzException("获取当前正在执行的任务失败", e);
        }
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * 启动调度器
     *
     * @param scheduler 调度器
     */
    private void startScheduler(Scheduler scheduler) {
        try {
            scheduler.start();
            logger.info("调度器启动成功");
        } catch (SchedulerException e) {
            throw new QuartzException("调度器启动失败", e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.startScheduler(scheduler);
    }
}
