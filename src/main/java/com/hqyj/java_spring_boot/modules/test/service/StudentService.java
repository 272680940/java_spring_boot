package com.hqyj.java_spring_boot.modules.test.service;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    //添加学生信息
    Result<Student> insertStudent(Student student);

    //通过ID查询学生信息
    Result<Student> getStudentByStudentId(int studentId);

    //分页查询
    Page<Student> getStudentBySearchVo(SearchVo searchVo);

    //jpa属性查询：通studentName查询学生信息
    List<Student> getStudentsByStudentName(String studentName,Integer cardId);

}
