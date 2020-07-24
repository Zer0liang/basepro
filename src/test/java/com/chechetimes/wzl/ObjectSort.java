package com.chechetimes.wzl;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * author:WangZhaoliang
 * Date:2020/7/4 17:07
 */
public class ObjectSort {

    private static List<Student> studentList;

    @Data
    static class Student {
        private String name;
        private int age;
        private int level;

        public Student(String name, int age, int level) {
            this.name = name;
            this.age = age;
            this.level = level;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", level=" + level +
                    '}';
        }
    }

    static {
        studentList = new ArrayList<Student>() {{
            add(new Student("张三", 10, 1));
            add(new Student("李四", 12, 2));
            add(new Student("王五", 15, 2));
            add(new Student("赵六", 16, 2));
        }};
    }

    @Test
    public void maopaoSort() {
        for (int i = 0; i < studentList.size(); i++) {
            for (int j = 0; j < studentList.size() - 1 - i; j++) {
                Student obj1 = studentList.get(j);
                Student obj2 = studentList.get(j+1);
                if (obj1.getLevel() < obj2.getLevel()) {
                    Student temp = obj1;
                    studentList.set(j, obj2);
                    studentList.set(j+1, temp);
                } else {
                    if (obj1.getAge() < obj2.getAge()) {
                        Student temp = obj1;
                        studentList.set(j, obj2);
                        studentList.set(j+1, temp);
                    }
                }
            }
        }
        printf(studentList);
    }

    @Test
    public void streamSort() {
        studentList.sort(Comparator.comparingInt(Student::getLevel).thenComparing(Comparator.comparing(Student::getAge).reversed()));
        printf(studentList);
    }

    private void printf(List<Student> studentList) {
        studentList.forEach(it -> System.out.println(it));
    }


}
