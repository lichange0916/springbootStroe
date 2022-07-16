package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理拦截器注册
 */
@Configuration //加载当前的配置类
public class InterceptorConfigurer implements WebMvcConfigurer {

    @Value("${file.path}")
    private String path;
    @Value("${file.static-path}")
    private String staticPath;

    /**
     * 配置虚拟路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticPath)
                .addResourceLocations("file:" + path);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        //配置白名单,存放在一个List集合
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/product/**");

        //拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")//表示要拦截的url是什么
                .excludePathPatterns(patterns);//表似乎要放行什么
    }

}
