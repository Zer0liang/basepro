package com.chechetimes.wzl.validator;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * author:WangZhaoliang
 * Date:2020/5/28 15:35
 * 某个字段只能填写固定的几个值
 */
@Component
@Log4j2
public class IdentifyFieldValueValidator implements ConstraintValidator<IdentifyFieldValue, String> {

    private Class enumClass;
    private String[] values;

    @Override
    public void initialize(IdentifyFieldValue constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(String objVal, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(objVal)) {
            return true;
        }

        String[] targetArr;
        //非某个注解类型
        if (enumClass == DefaultEnum.class) {
            targetArr = values;
        } else {
            targetArr = getAllText(enumClass);
        }

        String obj = Arrays.stream(targetArr).filter(it -> it.equals(objVal)).findAny().orElse(null);
        if (StringUtils.isEmpty(obj)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("字段值只能为:[" + StringUtils.join(targetArr, ",") + "]").addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }

    private static String[] getAllText(Class enumClass) {
        assert enumClass.isEnum();
        String[] arr = null;
        try {
            Enum[] enumConstants = (Enum[]) enumClass.getEnumConstants();
            arr = new String[enumConstants.length];
            Method method = enumClass.getMethod("toString");
            for (int i = 0; i < enumConstants.length; i++) {
                arr[i] = (String) method.invoke(enumConstants[i]);
            }
        } catch (Exception e) {
            log.error("IdentifyFieldValueValidator getAllText failed! enumClass:{}", enumClass.getName());
            e.printStackTrace();
        }
        return arr;
    }

}
