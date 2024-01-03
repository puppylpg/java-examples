package example.java8;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author puppylpg on 2021/07/28
 */
public class ComparatorDemo {

    public static void main(String... args) {

        List<Student> students = Arrays.asList(
                Student.builder().name("a").age(1).build(),
                Student.builder().name("b").age(3).build(),
                Student.builder().name("a").age(2).build()
        );

        students.sort(
                Comparator.comparing(Student::getName)
                        .thenComparing(Student::getAge)
        );
        System.out.println(students);

        students.sort(
                Comparator.comparing(Student::getName)
                        .thenComparing(Student::getAge)
                        // 上述所有的排序条件都逆序了。相当于每个comparator里都加了一个reverse order
                        .reversed()
        );
        System.out.println(students);

        students.sort(
                Comparator.comparing(Student::getName)
                        // 只有这个第二条件逆序了
                        .thenComparing(Student::getAge, Comparator.reverseOrder())
        );
        System.out.println(students);

    }

    @Data
    @Builder
    private static class Student {
        private String name;
        private int age;
    }
}
