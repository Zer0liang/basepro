package com.chechetimes.wzl.validator;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderType {

    /**
     * 保单类型
     */
    POLICY(0, "保单"),
    REVERSED_POLICY(1, "被冲正保单"),
    CORRECTION_POLICY(2, "冲正保单"),
    ENDORSEMENT(3, "批单"),
    REVERSED_ENDORSEMENT(4, "被冲正批单"),
    CORRECTION_ENDORSEMENT(5, "冲正批单"),
    CANCEL_POLICY(6, "退保"),
    ;


    private final int code;

    @EnumValue
    private final String text;

    OrderType(int code, String text) {
        this.code = code;
        this.text = text;
    }


    @JsonCreator
    public static OrderType get(int value) {
        return Arrays.stream(values()).filter(it -> it.getCode() == value).findAny().orElse(null);
    }

    public static OrderType get(String text) {
        return Arrays.stream(values()).filter(it -> it.getText().equals(text)).findAny().orElse(null);
    }

    @Override
    public String toString() {
        return text;
    }

}
