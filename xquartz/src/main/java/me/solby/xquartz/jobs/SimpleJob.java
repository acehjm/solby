package me.solby.xquartz.jobs;

import me.solby.xtool.json.JsonUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * me.solby.xquartz.service
 *
 * @author majhdk
 * @date 2019-06-20
 */
@Component
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("this is simple job");
        System.out.println(jobExecutionContext.getJobDetail());

        var ss = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(JsonUtil.toJson(ss));
    }
}