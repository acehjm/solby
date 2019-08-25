package me.solby.xconfig.manage;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * me.solby.xboot.manage
 *
 * @author majhdk
 * @date 2019-08-24
 */
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取应用程序上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取Bean对象
     *
     * @param className 类名称
     * @return
     */
    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

    /**
     * 根据类获取Bean对象
     *
     * @param tClass 类
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * 根据名称和类获取Bean对象
     *
     * @param name   名称
     * @param tClass 类
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> tClass) {
        return applicationContext.getBean(name, tClass);
    }
}
