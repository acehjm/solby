package me.solby.xquartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * todo 添加自定义任务添加功能
 *
 * @author majhdk
 * @date 2019-06-23
 */
@Component
public class WorkflowJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("this is workflow job");
    }

}
