package com.hqyj.java_spring_boot.filter;

import com.hqyj.java_spring_boot.interceptor.RequestViewInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * 过滤器
 * 该过滤器并没有注册到容器中，需要在配置类中加入过滤器
 */
@WebFilter(filterName = "requestParamFilter",urlPatterns = "/**")
//@Order(数值)   当有多个过滤器时，使用@Order(),数值越小，过滤器越优先
public class RequestParamFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestViewInterceptor.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("----过滤器的初始化init()方法----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("----过滤器的doFilter方法----");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        //此方法用不了，属性是锁住的
//        Map<String, String[]> parameterMap = httpRequest.getParameterMap();
//        parameterMap.put("paramKey",new String[]{"***"});

        //使用包装类HttpServletRequestWrapper
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest){
            //这个方法只能解决使用 request.getParameter() 取值替换，不能解决注解参数取值替换
            @Override
            public String getParameter(String name) {
                String paramValue = httpRequest.getParameter(name);
                if (StringUtils.isNotBlank(paramValue)){//判断参数是否为空
                    return paramValue.replaceAll("fuck","***");
                }
                return super.getParameter(name);
            }

            //解决注解参数取值替换
            @Override
            public String[] getParameterValues(String name) {
                String[] paramValues = httpRequest.getParameterValues(name);
                for (int i = 0;i<paramValues.length;i++) {
                    paramValues[i] = paramValues[i].replaceAll("fuck","***");
                    return paramValues;
                }
                return super.getParameterValues(name);
            }
        };

        filterChain.doFilter(wrapper,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("----过滤器的销毁destroy()方法----");
    }
}
