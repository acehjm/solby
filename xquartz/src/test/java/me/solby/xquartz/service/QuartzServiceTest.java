package me.solby.xquartz.service;

import me.solby.itool.json.JsonUtil;
import me.solby.xquartz.jobs.HttpJob;
import me.solby.xquartz.jobs.SimpleJob;
import me.solby.xquartz.jobs.WorkflowJob;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * me.solby.xquartz.service
 *
 * @author majhdk
 * @date 2019-06-23
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class QuartzServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceTest.class);

    @Autowired
    private QuartzService quartzService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addCronJob() {
        quartzService.addCronJob(SimpleJob.class, "jb1", "0 7 * * * ? ");
        quartzService.addCronJob(WorkflowJob.class, "jb1", "0 8 * * * ?  ");
        quartzService.addCronJob(HttpJob.class, "jb1", "0 13 * * * ? ");

    }

    @Test
    public void updateCronJob() {
        quartzService.updateCronJob(SimpleJob.class.getSimpleName(), "jb1", "0 10 * * * ? ");
        quartzService.updateCronJob(WorkflowJob.class.getSimpleName(), "jb1", "0 15 * * * ? ");
        quartzService.updateCronJob(HttpJob.class.getSimpleName(), "jb1", "0 16 * * * ? ");
    }

    @Test
    public void deleteJob() {
        quartzService.deleteJob(SimpleJob.class.getSimpleName(), "jb1");
    }

    @Test
    public void pauseJob() {
        quartzService.pauseJob(WorkflowJob.class.getSimpleName(), "jb1");
    }

    @Test
    public void resumeJob() {
        quartzService.resumeJob(WorkflowJob.class.getSimpleName(), "jb1");
    }

    @Test
    public void interruptJob() {
        quartzService.interruptJob(HttpJob.class.getSimpleName(), "jb1");
    }

    @Test
    public void allRunJob() {
        var list = quartzService.allExecutingJobs();
        logger.info(JsonUtil.toJson(list));
    }
}