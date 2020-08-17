package com.hqyj.java_spring_boot.modules.test.repository;

import com.hqyj.java_spring_boot.modules.test.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    //jpa属性查询:通过studentName查询学生信息
    List<Student> findByStudentName(String studentName);

    //jpa属性查询:通过studentName模糊查询
    List<Student> findByStudentNameLike(String studentName);

    //jpa属性查询:通过studentName模糊查询三条数据
    List<Student> findTop3ByStudentNameStartingWith(String studentName);

    //jpa自定义查询：通过多参数查询;
    //自定义查询增删改查都使用注解 @Query 编写语句，注意与sql不同
    /* 第一种写法：*/
    @Query(value = "select s from Student s where s.studentName = ?1 " +
            "and s.studentCard.cardId = ?2")//
    List<Student> getStudentsByParams(String student,Integer cardId);


    /* 第二种写法：
    @Query(value = "select s from Student s where s.studentName = :studentName " +
            "and s.studentCard.cardId = :cardId")//
    List<Student> getStudentsByParams(
            @Param("studentName") String studentName,
            @Param("cardId") Integer cardId);
    */

    /* 第三种写法：
    @Query( nativeQuery = true,value = "select * from h_student where student_name = :studentName " +
            "and card_id = :cardId")
    List<Student> getStudentsByParams(
            @Param("studentName") String studentName,
            @Param("cardId") Integer cardId);
    */



}
