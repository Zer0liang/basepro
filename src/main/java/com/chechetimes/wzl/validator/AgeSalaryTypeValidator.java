package com.chechetimes.wzl.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * author:WangZhaoliang
 * Date:2020/6/16 23:50
 */
public class AgeSalaryTypeValidator implements ConstraintValidator<AgeSalaryType, Student> {
    @Override
    public boolean isValid(Student stu, ConstraintValidatorContext context) {
        //只有满足age>= 18 并且 薪水低于100时，才会提示默认信息
        //当然这种是很简单的业务场景，一旦业务逻辑变得复杂，比如需要根据当前对象的某几个字段去查询数据库确定某个关系的时候，
        // 就显得颇为受用了
        if (stu.getAge() >= 18 &&
                (stu.getSalary() == null || stu.getSalary().compareTo(new BigDecimal(100)) < 0)) {
            return false;
        }
        return true;
    }
}
