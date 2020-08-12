package com.hqyj.java_spring_boot.modules.test.controller;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.test.entity.Student;
import com.hqyj.java_spring_boot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 添加学生信息
     * localhost/api/student ---- POST
     * {"studentName":"zhangshan","studentCard":{"cardId":"1"}}
     */
    @PostMapping(value = "/student",consumes = "application/json")
    public Result<Student> insertStudent(@RequestBody Student student){
        return studentService.insertStudent(student);
    }
}
