package me.solby.xconfig.manage;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义容器关闭时行为
 * 1。一般配置为等待线程处理完成后关闭
 * 2。在超时时间内未处理完，则强制关闭
 *
 * @author majhdk
 * @date 2019-07-26
 */
@Component
public class CustomShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CustomShutdown.class);

    /**
     * 线程超时时间
     */
    private static final int TIMEOUT = 30;

    /**
     * 连接器
     */
    private volatile Connector connector;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.connector.pause();
        var executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                logger.warn("Application ready to close");
                var threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    logger.warn("The application waits for " + TIMEOUT + " seconds and will force a shutdown");
                    threadPoolExecutor.shutdownNow();
                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        logger.error("Application shutdown failed");
                    }
                }
                if (threadPoolExecutor.isShutdown()) {
                    logger.warn("Application has been closed");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    /**
     * 将自定义Connector添加到内置Tomcat容器
     *
     * @param customShutdown
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final CustomShutdown customShutdown) {
        var tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addConnectorCustomizers(customShutdown);
        return tomcatServletWebServerFactory;
    }
}

