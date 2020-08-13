package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.Student;
import com.hqyj.java_spring_boot.modules.test.repository.StudentRepository;
import com.hqyj.java_spring_boot.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    //通过ID查询学生信息
    @Override
    public Result<Student> getStudentByStudentId(int studentId) {
        Student student = studentRepository.findById(studentId).get();
        return new Result<Student>(Result.ResultStatus.SUCCESS.status
                ,"select success!",student);
    }

    //分页查询
    @Override
    public Page<Student> getStudentBySearchVo(SearchVo searchVo) {
        //【StringUtils.isBlank()】判断字符串是否为空;  需要导入org.apache.commons.lang3的依赖
        //1、设置排序的字段：分页的默认排序【orderBy】字段为studentId;
        String orderBy = StringUtils.isBlank(searchVo.getOrderBy())?
                "studentId":searchVo.getOrderBy();

        //2、设置排序的规则：【sort】判断排序类型是否非空 或者 是否为升序(asc)排序
        Sort.Direction direction = StringUtils.isBlank(searchVo.getSort()) ||
                searchVo.getSort().equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        //3、将 排序的规则 和 排序的字段 封装到Sort对象中
        Sort sort = new Sort(direction,orderBy);

        //4、设置分页模型：起始页是从 0 开始的，所以 searchVo.getCurrentPage()-1 为当前页码
        //PageRequest.of(当前页码，每页显示条数，排序规则)
        Pageable pageable = PageRequest.of(searchVo.getCurrentPage()-1
                ,searchVo.getPageSize(),sort);

        Student student = new Student();
        //关键字查询：获取前端传来的关键字，如果 keyWord 为空，则设置的 studentName 不参与查询条件
        student.setStudentName(searchVo.getKeyWord());
        //匹配实体类的所有字段
        ExampleMatcher matcher = ExampleMatcher.matching()
                //与该实例对象的某条属性匹配，接收传来的 keyWord
                .withMatcher("studentName",match -> match.contains())
                .withIgnorePaths("studentId");//忽略实体类的某条属性
        //封装到模型中
        Example<Student> example = Example.of(student,matcher);

        return studentRepository.findAll(example,pageable);
    }


    @Override
    public List<Student> getStudentsByStudentName(String studentName,Integer cardId) {
        if(cardId>0){
            //jpa自定义查询
            return studentRepository.getStudentsByParams(studentName,cardId);
        }else{

            //jpa属性查询:通过studentName查询学生信息
//        return Optional.ofNullable(studentRepository.findByStudentName(studentName))
//                .orElse(Collections.emptyList());

//        //jpa属性查询:通过studentName模糊查询学生信息
//        //如此查不到结果，因为没有拼接上【 % % 】，因此要做一下转换
            //第一种方法：将字符串转换-> String.format("%s%S%s","%",studentName,"%")
//        return Optional.ofNullable(studentRepository
//                //将传递的参数拼接 ---- %参数%
//                .findByStudentNameLike(String.format("%s%S%s","%",studentName,"%")))
//                .orElse(Collections.emptyList());

            //jpa属性查询:通过studentName模糊查询前三条学生信息
            //第二种方法： 在StudentRepository类中使用 findTop3ByStudentNameStartingWith() 方法
            return Optional.ofNullable(studentRepository.findTop3ByStudentNameStartingWith(studentName))
                    .orElse(Collections.emptyList());
        }

    }
}
