package com.hqyj.java_spring_boot.modules.test.controller;

import com.hqyj.java_spring_boot.modules.test.entity.City;
import com.hqyj.java_spring_boot.modules.test.entity.Country;
import com.hqyj.java_spring_boot.modules.test.service.CityService;
import com.hqyj.java_spring_boot.modules.test.service.CountryService;
import com.hqyj.java_spring_boot.modules.test.vo.ApplicationTest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    /**
     * localhost/test/index
     */
    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap) {
        int countryId = 522;
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);

        modelMap.addAttribute("thymeleafTitle", "111111111111");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkbox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("BaiDuLogo",
                "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        modelMap.addAttribute("BaiDuLogo",
                "/upload/222.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
        //modelMap.addAttribute("template", "test/index");
        //返回外层的碎片组装器
        return "index";
    }

    /**
     * localhost/test/index2
     */
    @GetMapping("/index2")
    public String testIndex2Page(ModelMap modelMap) {
        modelMap.addAttribute("template", "test/index2");
        return "index";
    }

    /**
     * 过滤器Filter
     * 需求：对前端传来的敏感字符 “fuck” 进行过滤
     * localhost/test/testFilter?paramKey=fuck --- GET
     */
    @GetMapping("/testFilter")
    @ResponseBody
    public String testFilter(@RequestParam(value = "paramKey") String paramValue,
                             HttpServletRequest request){

        String paramValue2 = request.getParameter("paramKey");
        return "请求的参数为"+paramValue2+"=="+paramValue;
    }

    /**
     * 单个文件上传
     * 参数注解 @RequestParam
     * 接收参数 MultipartFile file
     * consumes = "multipart/form-data"
     * localhost/test/index ---- POST
     */
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public String uploadFile(@RequestParam MultipartFile file
            ,RedirectAttributes redirectAttributes){

        //判断是否上传文件
        if (file.isEmpty()){
            //没有文件上传提示信息e
            redirectAttributes.addFlashAttribute("message","no file");
            return "redirect:/test/index";
        }

        try {
            //目标文件路径
            String descFilePath = "D:\\upload\\"+file.getOriginalFilename();
            //目标文件
            File descFile = new File(descFilePath);
            //上传文件
            file.transferTo(descFile);
            //上传成功提示信息
            redirectAttributes.addFlashAttribute("message","upload success");
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常，上传失败提示信息
            redirectAttributes.addFlashAttribute("message","upload failed");
        }

        return "redirect:/test/index";
    }

    /**
     * 多文件上传
     */
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files,
                              RedirectAttributes redirectAttributes){
        //假定没有上传文件
        boolean empty = true;

        try {
            //遍历上传的文件
            for (MultipartFile file : files) {
                //判断上传的文件是否为空
                if (file.isEmpty()){
                    //为空，则判断下一个上传的文件
                    continue;
                }

                //目标文件路径, 获取源文件名称：file.getOriginalFilename()
                String descFilePath = "D:\\upload\\" + file.getOriginalFilename();

                //目标文件
                File descFile = new File(descFilePath);

                //上传文件
                file.transferTo(descFile);

                //有文件可以上传
                empty = false;
            }
            //判断是否有文件上传
            if (empty){
                //无文件上传
                redirectAttributes.addFlashAttribute("message","no file");
            }else{
                //至少有一个文件上传
                redirectAttributes.addFlashAttribute("message","upload success");
            }
            } catch (IOException e) {
                e.printStackTrace();
                //出现异常，上传失败
                redirectAttributes.addFlashAttribute("message","upload error");
            }

        return "redirect:/test/index";
    }



    /**
     * 文件下载
     */
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource(
                    //下载文件的具体位置
                    Paths.get("D:\\upload\\" + fileName).toUri());
            //判断资源存在且是可读的
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                String.format("attachment; filename=\"%s\"", resource.getFilename()))
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以包装类 IOUtils 输出文件
     */
    @RequestMapping("/download2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        String filePath = "D:/upload" + File.separator + fileName;
        File downloadFile = new File(filePath);

        try {
            if (downloadFile.exists()) {
                response.setContentType("application/octet-stream");
                response.setContentLength((int)downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", fileName));

                InputStream is = new FileInputStream(downloadFile);
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
