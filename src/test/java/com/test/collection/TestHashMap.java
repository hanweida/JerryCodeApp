package com.test.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Objects;

public class TestHashMap {
    @Test
    public void testHash(){
        HashMap<Student, Integer> stu = new HashMap<Student, Integer>(1);
        Student student = new Student();
        student.setNo(1);
        student.setName("a");
        stu.put(student, 1);

        Student student2 = new Student();
        student2.setNo(2);
        student2.setName("a");
        stu.put(student2, 2);

        Student student3 = new Student();
        student3.setNo(3);
        student3.setName("a");
        stu.put(student3, 3);

        Student student4 = new Student();
        student4.setNo(4);
        student4.setName("b");
        stu.put(student4, 4);

        Student student5 = new Student();
        student5.setNo(5);
        student5.setName("b");
        stu.put(student5, 5);

        Student student6 = new Student();
        student6.setNo(6);
        student6.setName("b");
        stu.put(student6, 6);

        Student student7 = new Student();
        student7.setNo(7);
        student7.setName("b");
        stu.put(student7, 7);
    }
}

class Student{
    private Integer no;
    private String name;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(no, student.no) &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

