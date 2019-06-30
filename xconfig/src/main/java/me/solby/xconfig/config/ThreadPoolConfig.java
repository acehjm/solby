package me.solby.xconfig.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * <p>
 * 线程池执行线程顺序：
 * 1. 当线程数小于核心线程数时，创建线程
 * 2. 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列
 * 3. 当线程数大于等于核心线程数，且任务队列已满
 * - 若线程数小于最大线程数，创建线程
 * - 若线程数等于最大线程数，抛出异常，拒绝任务
 *
 * @author majhdk
 * @date 2019-06-30
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        logger.info("configuration taskExecutor ...");

        var executor = new ThreadPoolTaskExecutor();
        /*
         * 配置核心线程数
         * - 核心线程会一直存活，及时没有任务需要执行
         * - 当线程数小于核心线程数时，即使有线程空闲，线程池也会优先创建新线程处理
         * - 设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
         */
        executor.setCorePoolSize(5);
        /*
         * 最大线程数
         * - 当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
         * - 当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
         */
        executor.setMaxPoolSize(5);
        /*
         * 任务队列容量（阻塞队列）
         * - 当核心线程数达到最大时，新任务会放在队列中排队等待执行
         */
        executor.setQueueCapacity(20);
        /*
         * 线程空闲时间
         * - 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
         * - 如果allowCoreThreadTimeout=true，则会直到线程数量=0
         */
        executor.setKeepAliveSeconds(60);
        /*
         * 默认为false
         * 设置为true则线程池会回收核心线程池的线程，false则只会回收超过核心线程池的线程
         */
        executor.setAllowCoreThreadTimeOut(false);

        /*
         * 配置线程池中的线程的名称前缀
         */
        executor.setThreadNamePrefix("async-thread-");

        /*
         * 任务拒绝处理器(当pool已经达到max size的时候，如何处理新任务)
         * 两种情况会拒绝处理任务:
         *   - 当线程数已经达到maxPoolSize，切队列已满，会拒绝新任务;
         *   - 当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown;
         *   - 如果在调用shutdown()和线程池真正shutdown之间提交任务，会拒绝新任务;
         * 线程池会调用rejectedExecutionHandler来处理这个任务。如果没有设置默认是AbortPolicy，会抛出异常;
         * ThreadPoolExecutor类有几个内部实现类来处理这类情况：
         *  - AbortPolicy 丢弃任务并抛出RejectedExecutionException
         *  - CallerRunsPolicy 只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务;
         *    显然这样做不会真的丢弃任务，但是，任务提交线程的性能极有可能会急剧下降;
         *  - DiscardPolicy 丢弃任务，不做任何处理;
         *  - DiscardOldestPolicy 丢弃队列中最老的一个请求，也就是即将被执行的一个任务，并尝试再次提交当前任务;
         * 实现RejectedExecutionHandler接口，可自定义处理器
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        /*
         * 等待所有任务执行完成后关闭线程池
         */
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }

    //-------------------------------------------------------------------------private

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "thread.pool")
    public static class ThreadPoolProperties {
        /**
         * 核心线程池
         */
        private Integer corePoolSize = 5;
        /**
         * 最大线程池
         */
        private Integer maxPoolSize = 5;
        /**
         * 任务队列容量（阻塞队列）
         */
        private Integer queueCapacity = 20;
        /**
         * 线程空闲时间
         */
        private Integer keepAliveSeconds = 60;
        /**
         * 是否允许核心线程池超时
         */
        private Boolean allowCoreThreadTimeOut = false;
        /**
         * 配置线程池中的线程的名称前缀
         */
        private String threadNamePrefix = "thread-";
        /**
         * 等待所有任务执行完成后关闭线程池
         */
        private Boolean waitForTasksToCompleteOnShutdown = false;
        /**
         * 拒绝策略
         * - AbortPolicy 丢弃任务并抛出RejectedExecutionException
         * - CallerRunsPolicy 只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务;
         * 显然这样做不会真的丢弃任务，但是，任务提交线程的性能极有可能会急剧下降;
         * - DiscardPolicy 丢弃任务，不做任何处理;
         * - DiscardOldestPolicy 丢弃队列中最老的一个请求，也就是即将被执行的一个任务，并尝试再次提交当前任务;
         */
        private String rejectedExecutionHandler = REHEnum.AbortPolicy.name();

        @Bean("xTaskExecutor")
        public Executor customTaskExecutor() {
            logger.info("configuration custom task configuration ...");
            var executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(corePoolSize);
            executor.setMaxPoolSize(maxPoolSize);
            executor.setQueueCapacity(queueCapacity);
            executor.setKeepAliveSeconds(keepAliveSeconds);
            executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
            executor.setThreadNamePrefix(threadNamePrefix);
            executor.setRejectedExecutionHandler(REHEnum.get(rejectedExecutionHandler));
            executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
            //执行初始化
            executor.initialize();
            return executor;
        }

        /**
         * RejectedExecutionHandler Enum
         */
        enum REHEnum {
            AbortPolicy(new ThreadPoolExecutor.AbortPolicy()),
            CallerRunsPolicy(new ThreadPoolExecutor.CallerRunsPolicy()),
            DiscardPolicy(new ThreadPoolExecutor.DiscardPolicy()),
            DiscardOldestPolicy(new ThreadPoolExecutor.DiscardOldestPolicy());

            private RejectedExecutionHandler rejectedExecutionHandler;

            REHEnum(RejectedExecutionHandler handler) {
                this.rejectedExecutionHandler = handler;
            }

            public static RejectedExecutionHandler get(String handlerName) {
                return Arrays.stream(REHEnum.values())
                        .filter(it -> it.name().equals(handlerName))
                        .findFirst()
                        .orElse(AbortPolicy)
                        .getRejectedExecutionHandler();
            }

            RejectedExecutionHandler getRejectedExecutionHandler() {
                return rejectedExecutionHandler;
            }
        }

    }
}
