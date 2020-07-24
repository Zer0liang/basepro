package com.chechetimes.wzl.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author:WangZhaoliang
 * Date:2020/5/28 15:24
 * 校验只有当年龄大于18时，如果薪水小于100，就提示错误信息
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeSalaryTypeValidator.class)
@Documented
public @interface AgeSalaryType {

    String message() default "当年龄大于18岁时，每月薪水不得低于100元";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        AgeSalaryType[] value();
    }

}
