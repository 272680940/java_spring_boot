package com.hqyj.java_spring_boot.modules.test.controller;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.Student;
import com.hqyj.java_spring_boot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import java.util.List;


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

    /**
     *通过studentId查询学生信息
     * localhost/api/student/1 ---- GET
     */
    @GetMapping(value = "/student/{studentId}")
    public Result<Student> getStudentByStudentId(@PathVariable int studentId){
        return studentService.getStudentByStudentId(studentId);
    }

    /**
     * 分页查询学生信息
     * localhost/api/students ---- POST
     * {"currentPage":"1","pageSize":"5","keyWord":"zh","orderBy":"studentName","sort":"desc"}
     */
    @PostMapping(value = "/students",consumes = "application/json")
    public Page<Student> getStudentBySearchVo(@RequestBody SearchVo searchVo){
        return studentService.getStudentBySearchVo(searchVo);
    }

    /**
     * jpa属性查询：
     * 1、通过studentName查询学生信息 --> findByStudentName()
     * 2、通过studentName模糊查询 --> findByStudentNameLike()
     * 3、通过studentName模糊查询前三条学生信息 --> findTop3ByStudentNameStartingWith()
     *
     * jap自定义查询
     * 1、通过多参数查询学生信息 --> getStudentsByParams()
     * localhost/api/students?studentName=zhangshan ---- GET
     */
    @GetMapping(value = "/students")
    public List<Student> getStudentsByParams(
            @RequestParam String studentName,
            //【required = false】表示该属性不是必需的
            //【defaultValue = "1"】设置默认值
            @RequestParam(required = false,defaultValue = "1") Integer cardId){
        return studentService.getStudentsByStudentName(studentName,cardId);
    }
}
