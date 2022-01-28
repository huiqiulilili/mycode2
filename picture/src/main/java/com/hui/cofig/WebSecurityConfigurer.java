package com.hui.cofig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .mvcMatchers("/login.html").permitAll()
                    .mvcMatchers("/index").permitAll() // 放行资源
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.html") // 用来指定默认的登录页面,注意：一旦自定义登录页面后必须指定登陆 url
                    .loginProcessingUrl("/doLogin") // 指定处理登陆请求 url
                    .usernameParameter("uname")
                    .passwordParameter("pwd")
                    // .successForwardUrl("/index") // 认证成功 forWord【地址栏不变】 跳转路径
                    // .defaultSuccessUrl("/index",true) // 默认认证成功 redirect【地址栏变】 之后跳转
                     .successHandler(new MyAuthenticationSuccessHandler()) // 认证成功时的处理 前后端分离的处理
                    // .failureForwardUrl("/login.html") //认证失败之后的forWord跳转
                    // .failureUrl("/login.html") // 认证失败之后的redirect跳转【默认】
                    .failureHandler(new MyAuthenticationFailureHandler()) // 用来自定义失败之后处理 前后端分离解决方案
                    .and()
                    .logout()
                    // .logoutUrl("/logout") // 指定注销登陆url 默认请求方式必须是get
                    .logoutRequestMatcher(new OrRequestMatcher(
                            new AntPathRequestMatcher("/aa","GET"),
                            new AntPathRequestMatcher("/bb","POST")
                    ))
                    .invalidateHttpSession(true) // 默认 回话失效
                    .clearAuthentication(true) // 默认  清除认证标记
                    // .logoutSuccessUrl("/login.html") // 注销登陆成功之后 跳转页面
                    .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 注销成功之后处理 前后端分离
                    .and()
                    .csrf().disable();// 禁止 csrf 跨站请求保护

    }

}
