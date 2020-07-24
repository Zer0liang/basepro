package com.chechetimes.wzl.validator;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * author:WangZhaoliang
 * Date:2020/6/18 21:45
 */
public class StudentGsProvider implements DefaultGroupSequenceProvider<Student> {
    @Override
    public List<Class<?>> getValidationGroups(Student student) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(Student.class);

        if (student != null && student.getAge() >= 18) {
            defaultGroupSequence.add(AddGroup.class);
        }

        return defaultGroupSequence;
    }
}
