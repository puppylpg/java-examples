package xyz.puppylpg.record;

/**
 * @author liuhaibo on 2023/08/10
 */
public class RecordMain {

    public static void main(String... args) {
        var student = new Student("record nice", 1);
        System.out.println(student.name());
    }
}
