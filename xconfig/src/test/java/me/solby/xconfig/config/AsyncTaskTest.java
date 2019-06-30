package me.solby.xconfig.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * me.solby.xconfig.config
 *
 * @author majhdk
 * @date 2019-06-30
 */
@Component
public class AsyncTaskTest {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskTest.class);

    @Async("xTaskExecutor")
    public void test04() {
        logger.info("进入test04方法");
        logger.info("test04-在执行异步代码块之前结束");
    }

    @Async
    public void test05() {
        logger.info("进入test05方法");
        logger.info("test05-在执行异步代码块之前结束");
    }

}
