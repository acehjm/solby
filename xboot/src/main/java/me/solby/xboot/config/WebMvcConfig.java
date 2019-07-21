package me.solby.xboot.config;

import me.solby.xboot.config.converter.XJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * me.solby.xboot.config
 *
 * @author majhdk
 * @date 2019-07-04
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    /**
     * 添加拦截器
     * 多个拦截器组成一个拦截器链
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(RequestLogInterceptor()).addPathPatterns("/**")
//        registry.addInterceptor(TokenHandlerInterceptor()).addPathPatterns("/**")
//        super.addInterceptors(registry);
    }

    /**
     * 添加自定义消息解析器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // add方法可以指定顺序，有多个自定义的WebMvcConfigurerAdapter时，可以改变相互之间的顺序
        // 但是都在springmvc内置的converter前面
        logger.info("加载自定义消息转换器");
        converters.add(new XJackson2HttpMessageConverter());

    }

}
