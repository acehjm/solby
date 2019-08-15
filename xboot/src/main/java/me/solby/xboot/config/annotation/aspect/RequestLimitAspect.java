package me.solby.xboot.config.annotation.aspect;

import me.solby.xboot.config.annotation.RequestLimit;
import me.solby.xboot.config.annotation.enums.LimitTypeEnum;
import me.solby.xconfig.config.exception.BusinessException;
import me.solby.xoauth.common.UserHolder;
import me.solby.xoauth.jwt.JwtUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * todo 添加用户或客户端或租户限制
 *
 * @author majhdk
 * @date 2019-08-11
 */
@Aspect
@Component
public class RequestLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class);

    /**
     * 接口调用频率键
     */
    private static final String CALL_FREQUENCY_LIMIT_KEY = "CALL_FREQUENCY_LIMIT:";

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private HttpServletRequest servletRequest;

    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *) && @annotation(requestLimit)")
    public void aspectRequest(RequestLimit requestLimit) {
    }

    /**
     * 接口调用限制，配置环绕通知
     *
     * @param joinPoint
     * @param requestLimit
     */
    @Around(value = "aspectRequest(requestLimit)", argNames = "joinPoint,requestLimit")
    public Object callBefore(ProceedingJoinPoint joinPoint, RequestLimit requestLimit) throws Throwable {
        int callCount = requestLimit.count();
        int time = requestLimit.time();
        TimeUnit unit = requestLimit.unit();
        LimitTypeEnum typeEnum = requestLimit.type();

        JwtUser user = UserHolder.userThreadLocal.get();

        String callLimitKey = CALL_FREQUENCY_LIMIT_KEY + servletRequest.getRequestURI();
        switch (typeEnum) {
            case ACCOUNT_INTERFACE:
                callLimitKey = callLimitKey + ":" + user.getUsername();
                break;
            case INTERFACE:
                // default callLimitKey
                break;
            default:
                break;
        }

        Integer remainCount = redisTemplate.boundValueOps(callLimitKey).get();
        if (null == remainCount) {
            redisTemplate.boundValueOps(callLimitKey).set(callCount, time, unit);
        } else if (remainCount <= 0) {
            throw new BusinessException("接口调用已达上限");
        }
        remainCount = redisTemplate.boundValueOps(callLimitKey).get();
        if (null != remainCount && remainCount > 0) {
            redisTemplate.boundValueOps(callLimitKey).decrement();
        }
        logger.info("[{}]调用接口[{}],限制{}次/{}{}, 已调用{}次", user.getUsername(), servletRequest.getRequestURI(),
                callCount, time, unit.name(), callCount - (remainCount == null ? 0 : remainCount - 1));
        return joinPoint.proceed();
    }

}
