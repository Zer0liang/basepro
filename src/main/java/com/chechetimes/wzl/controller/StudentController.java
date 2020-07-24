package com.chechetimes.wzl.controller;

import com.chechetimes.wzl.validator.AddGroup;
import com.chechetimes.wzl.validator.Student;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;

/**
 * author:WangZhaoliang
 * Date:2020/6/17 21:01
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @RequestMapping("/add")
    public Map<String, Object> addStudent(@Validated({Default.class, AddGroup.class}) Student student, BindingResult bindingResult) {
        Map<String, Object> resultMap = new HashMap<>(10);
        resultMap.put("success", true);
        if (bindingResult.hasErrors()) {
            resultMap.put("success", false);
            StringBuilder stringBuilder = new StringBuilder();
            bindingResult.getAllErrors().stream().forEach(it -> stringBuilder.append(it.getDefaultMessage()));
            resultMap.put("message", stringBuilder.toString());
            return resultMap;
        }
        return resultMap;
    }

}
