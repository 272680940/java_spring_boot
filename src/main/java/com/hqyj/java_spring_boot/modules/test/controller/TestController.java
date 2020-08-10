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

    /* 使用 @Value(${key}),获取全局配置文件的值 */
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
        String msg = "";
        msg=applicationTest.getInfo()+"----"+"随机数："+applicationTest.getRandom()
                +"----"+"端口号："+applicationTest.getServerPort();
        return msg;
    }

    /**
     * 测试：日志打印
     * @return
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest(){
        LOGGER.trace("This is TRACE log");
        LOGGER.trace("This is DEBUG log");
        LOGGER.trace("This is INFO log");
        LOGGER.trace("This is WARN log");
        LOGGER.trace("This is ERROR log");
        return "This is log test";
    }


}
