package com.chechetimes.wzl.validator

import app.SpringApplicationLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validator
import javax.validation.groups.Default

/**
 * author:WangZhaoliang
 * Date:2020/6/16 11:53
 */
@SpringBootTest(classes = SpringApplicationLauncher)
class StudentValidator extends Specification {

    @Autowired
    private Validator validator

    def testStudent() {
        Student student = new Student();
        student.setName("你哈")
        student.setAge(20);
        student.setSalary(new BigDecimal(50));

        Set<ConstraintViolation<Student>> result = validator.validate(student);
        printfError(result);

        expect:
        true
    }

    def printfError(Set<ConstraintViolation<Student>> result) {
        System.out.println("==================");
        for (ConstraintViolation it : result) {
            System.out.println(it.message);
        }
    }

}
