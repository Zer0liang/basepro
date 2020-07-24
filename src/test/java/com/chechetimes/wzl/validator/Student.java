package com.chechetimes.wzl.validator;

import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.math.BigDecimal;

/**
 * author:WangZhaoliang
 * Date:2020/6/16 11:47
 */
@Data
@AgeSalaryType(groups = AddGroup.class)
@GroupSequence({AddGroup.class, UpdateGroup.class, Student.class})
public class Student {

    @NotNull(message = "id主键不能为空", groups = UpdateGroup.class)
    private Long id;

    @Size(min = 5, max = 10, message = "姓名长度在5-10")
    private String name;

    @NotNull
    @Min(value = 5, message = "年龄不能低于5岁")
    private int age;

    @IdentifyFieldValue(enumClass = OrderType.class)
    private String orderType;

    @NotNull
    @Digits(integer = 10, fraction = 2, message = "请保留小数点后2位")
    private BigDecimal salary;

}
