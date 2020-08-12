package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.test.entity.Student;
import com.hqyj.java_spring_boot.modules.test.repository.StudentRepository;
import com.hqyj.java_spring_boot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //添加学生信息
    @Override
    @Transactional
    public Result<Student> insertStudent(Student student) {
        // LocalDateTime.now() 与 new Date() 效果一致，新的写法
        student.setCreateDate(LocalDateTime.now());//获取当前时间
        studentRepository.saveAndFlush(student);
        return new Result<Student>(Result.ResultStatus.SUCCESS.status
                ,"insert success!",student);
    }
}
