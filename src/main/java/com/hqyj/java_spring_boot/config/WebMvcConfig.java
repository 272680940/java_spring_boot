package com.hqyj.java_spring_boot.config;

import com.hqyj.java_spring_boot.filter.RequestParamFilter;
import com.hqyj.java_spring_boot.interceptor.RequestViewInterceptor;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${server.http.port}")
    private int port;

    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Bean
    public Connector connector(){
        Connector connector = new Connector();
        connector.setPort(port);
        connector.setScheme("http");
        return connector;
    }

    @Bean
    public ServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector());
        return  tomcat;
    }

    /**
     * 将过滤器 RequestParamFilter 注册到容器中
     */
    @Bean
    public FilterRegistrationBean<RequestParamFilter> registBean(){
        FilterRegistrationBean<RequestParamFilter> register =
                new FilterRegistrationBean<RequestParamFilter>();
        register.setFilter(new RequestParamFilter());
        return register;
    }

    @Autowired
    private RequestViewInterceptor requestViewInterceptor;
    /**
     * 将拦截器注册到容器中
     * (1)实现 WebMvcConfigurer 接口
     * (2)引入自定义的拦截器 RequestViewInterceptor
     * (3)注册拦截器: registry.addInterceptor(自定义的拦截器)
     * (4)设置拦截器URL匹配规则 addPathPatterns()
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestViewInterceptor).addPathPatterns("/**");
    }

    /**
     * 文件上传与下载
     * 添加本地资源文件
     * 需求：
     *      当从页面上传文件到指定本地路径 D:/upload 时, 映射就变成了配置文件
     *      中的相对路径: /upload/** , 前端获取到了这个相对路径后就可以到本地
     *      路径 D:/upload 中找到对应的文件渲染到页面上.
     *      而数据库保存的是相对路径: /upload/** ,
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String systemName = System.getProperty("os.name");
        if (systemName.toLowerCase().startsWith("win")){
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX+resourceConfigBean.getLocationPathForWindows());
        }else{
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX+resourceConfigBean.getLocationPathForLinux());
        }
    }
}
