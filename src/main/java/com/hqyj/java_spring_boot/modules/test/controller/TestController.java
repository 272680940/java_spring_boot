package com.hqyj.java_spring_boot.modules.test.controller;

import com.hqyj.java_spring_boot.modules.test.vo.ApplicationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    /* 使用 @Value(${key}),获取全局配置文件的值*/
    @Value("${com.info}")
    private String info;

    @Value("${com.random}")
    private String random;

    @Value("${server.port}")
    private  int serverPort;

    /* 使用 @Autowired 加载 非全局配置文件*/
    @Autowired
    private ApplicationTest applicationTest;

    /* 日志文件应用 */
    /* 测试日志文件 */
    // 1、引入Logger，注意jar包：org.slf4j.Logger;
    // 2、变量名称全大写，如：LOGGER
    // 3、LoggerFactory 注意jar包：org.slf4j.LoggerFactory;
    // 4、getLogger(当前类.class)
    private final static Logger LOGGER =LoggerFactory.getLogger(TestController.class);

    /**
     * 测试程序
     * @return
     */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(){
        return "spring boot test";
    }


    /**
     * 测试：读取全局配置文件的信息
     * @return
     */
    @GetMapping("/testGetProp")
    @ResponseBody
    public String testGetProp(){
        String msg = "";
        msg=info+"----"+"随机数："+random+"----"+"端口号："+serverPort;
        return msg;
    }

    /**
     * 测试：读取非全局配置文件的信息
     * @return
     */
    @GetMapping("/testGetProp2")
    @ResponseBody
    public String testGetProp2(){
        StringBuffer sb = new StringBuffer();
        sb.append(applicationTest.getInfo()).append(applicationTest.getRandom())
               .append(applicationTest.getServerPort());
        return sb.toString();
    }

    /**
     * 测试：日志打印
     * @return
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest(){
        /*
         * 	level: TRACE<DEBUG<INFO<WARN<ERROR
         * 	我们在 appender file 的时候，并没有指定日志级别，在此由 root来控制，输出指定级别及之上级别的日志
         */
        LOGGER.trace("this is TRACE log.");
        LOGGER.debug("this is DEBUG log.");
        LOGGER.info("this is INFO log.");
        LOGGER.warn("this is WARN log.");
        LOGGER.error("this is ERROR log.");
        return "this is log test!!!";
    }


}
