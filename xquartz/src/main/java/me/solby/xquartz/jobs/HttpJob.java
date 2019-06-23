package me.solby.xquartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * me.solby.xquartz.jobs
 *
 * @author majhdk
 * @date 2019-06-23
 */
@Component
public class HttpJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("this is http job");
    }

}
