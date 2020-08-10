package com.hqyj.java_spring_boot.modules.test.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/applicationTest.properties")
@ConfigurationProperties(prefix = "com.msg")//配置非全局配置文件的属性，加上前缀prefix
public class ApplicationTest {

//    @Value("${com.info}")
//    private String info;
//
//    @Value("${com.random}")
//    private String random;
//
//    @Value("${server.port}")
//    private  int serverPort;

    private String info;
    private String random;
    private  int serverPort;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
