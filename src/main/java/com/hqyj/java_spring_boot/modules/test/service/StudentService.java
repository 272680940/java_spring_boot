package com.hqyj.java_spring_boot.modules.test.service;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.test.entity.Student;

public interface StudentService {
    Result<Student> insertStudent(Student student);
}
