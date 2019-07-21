package me.solby.xoauth.config;

import me.solby.xoauth.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全配置
 *
 * @author majhdk
 * @date 2019-05-25
 */
@Configuration
@EnableWebSecurity
//开启方法级的权限注解  设置后控制器层的方法前的@PreAuthorize("hasRole('admin')") 注解才能起效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //认证不通过后的处理
                .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //配置哪些路径可以不用权限访问
                .antMatchers("/css/**", "/fonts/**", "/js/**", "/index.html").permitAll()
                //所有 /login, /verify/** 的请求 都放行
                .antMatchers("/login", "/verify/**", "/druid/**", "/auth/**").permitAll()
                //以"/admin/" 开头的URL只能由拥有 "ROLE_ADMIN"角色的用户访问.
                .antMatchers("/admin/**").hasRole("ADMIN")
                //任何以/db/开头的URL需要用户同时具有"ROLE_ADMIN" 和 "ROLE_DBA".
                .antMatchers("/db/**").hasAnyRole("ADMIN", "DBA")
                //尚未匹配的任何URL要求用户进行身份验证
                .anyRequest().fullyAuthenticated();
        //添加JWT过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }

    /**
     * 配置Spring Security的Filter链
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/exception", "/favicon.ico", "/webjars/**");
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        // 配置指定用户权限信息  通常生产环境都是从数据库中读取用户权限信息而不是在这里配置

        // ****************   基于数据库中的用户权限信息 进行认证
        //指定密码加密所使用的加密器为 passwordEncoder()
        //需要将密码加密后写入数据库
        // userDetailService 类中获取了用户的用户名、密码以及是否启用的信息，查询用户所授予的权限，用来进行鉴权，查询用户作为群组成员所授予的权限
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }


    //-------------------------------------------------------------------------inner class;

    /**
     * Rest授权认证
     */
    class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

        /**
         * 认证失败返回信息
         *
         * @param request
         * @param response
         * @param authException
         */
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: " + authException.getMessage());
        }
    }


}
