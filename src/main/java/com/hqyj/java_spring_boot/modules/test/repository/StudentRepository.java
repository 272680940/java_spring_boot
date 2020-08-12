package com.hqyj.java_spring_boot.modules.test.repository;

import com.hqyj.java_spring_boot.modules.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
