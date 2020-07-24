package com.chechetimes.wzl.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author:WangZhaoliang
 * Date:2020/5/28 15:24
 * 校验某个字段只能是固定的某些值
 * 比如：是否有车牌，只能填写“是”或者“否”
 * 如果是某个枚举类型里面的text值，就设置enumClass；如果是自定义的某些值，就设置values数组
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentifyFieldValueValidator.class)
@Documented
public @interface IdentifyFieldValue {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    /**
     * 某个枚举类型
     * @return
     */
    Class enumClass() default DefaultEnum.class;

    /**
     * 固定的某些值
     */
    String[] values() default {};

    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IdentifyFieldValue[] value();
    }

}
