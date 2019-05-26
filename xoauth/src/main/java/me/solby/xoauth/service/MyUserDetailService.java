package me.solby.xoauth.service;

import me.solby.xoauth.domain.UserDAO;
import me.solby.xoauth.domain.entity.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证实现类
 * + 以下说明为通用服务时生效，嵌入式服务只需更新方法内容；
 * - 具体由业务方实现，这里做空实现或抛异常；
 * - 实现类必须在bean申明时，配置名称，如：@Service("userDetailsService")，否则有可能会出错；
 * - 如果该服务作为jar包提供，注释掉@Service("userDetailsService")，由业务方实现；
 *
 * @author majhdk
 * @date 2019-05-25
 */
@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户 {} 进入认证阶段", username);
        UserDO userDO = userDAO.findByUserName(username);

        if (ObjectUtils.isEmpty(userDO)) {
            logger.warn("用户 {} 不存在", username);
            return null;
        }
        //添加用户授权
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("GUEST"));

        String password = new BCryptPasswordEncoder().encode(userDO.getPassword());
        //通过User对象方法可以配置其它行为
        return new User(userDO.getUserName(), password, authorities);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        throw new UsernameNotFoundException("【默认实现】用户不存在，请重写次方法");
//    }
}
