package me.solby.xconfig.manage;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * 全局初始化
 *
 * @author majhdk
 * @date 2019/8/27
 */
@Component
public class InitializationConfig {

    /**
     * 执行初始化行为
     */
    @PostConstruct
    public void init() {
        // set code prefix
        ResponseHolder.setCodePrefix("0x0f");
        // set default locale
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        // TODO OTHER CONFIGS
    }

}
