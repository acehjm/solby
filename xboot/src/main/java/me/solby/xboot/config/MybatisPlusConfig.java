package me.solby.xboot.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-11-24
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置分页
     * -。xml中可以从里面进行取值,传递参数
     * -。Page 即自动分页,必须放在第一位(可以继承Page实现自己的分页对象)
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 配置逻辑删除
     * -。0表示未删除状态；-1表示逻辑删除状态；
     * -。在实体字段上加上@TableLogic注解
     *
     * @return
     */
    @Bean
    public ISqlInjector iSqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * SQL执行效率插件
     *
     * @return
     */
    @Bean
    @Profile({"dev", "test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        interceptor.setMaxTime(100);  //SQL执行最大时长，超过该时间自动停止
        interceptor.setFormat(true);  //是否格式化SQL
        return interceptor;
    }

    /**
     * 乐观锁配置
     * -。当要更新一条记录的时候，希望这条记录没有被别人更新
     *  。取出记录时，获取当前version
     *  。更新时，带上这个version
     *  。执行更新时， set version = newVersion where version = oldVersion
     *  。如果version不对，就更新失败
     * -。解实体字段 @Version 必须要
     *  。支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     *  。整数类型下 newVersion = oldVersion + 1
     *  。newVersion 会回写到 entity 中
     *  。仅支持 updateById(id) 与 update(entity, wrapper) 方法
     *  。在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}

