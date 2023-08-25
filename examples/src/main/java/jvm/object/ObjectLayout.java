package jvm.object;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author liuhaibo on 2023/08/24
 */
public class ObjectLayout {

    static A a = new A();

    /**
     * 可选指针压缩参数：
     * -XX:-UseCompressedOops
     * -XX:-UseCompressedClassPointers
     * <p>
     * https://puppylpg.github.io/2023/08/24/java-object/
     */
    public static void main(String... args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
