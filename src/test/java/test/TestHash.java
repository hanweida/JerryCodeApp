package test;

import org.junit.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TestHash {

    @Test
    public void test(){
        Set stuSet = new HashSet<Student>();
        Student student = new Student();
        student.setNo(1);
        student.setName("a");
        stuSet.add(student);
        student.setNo(2);
        stuSet.add(student);
        System.out.println(stuSet.size());
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
