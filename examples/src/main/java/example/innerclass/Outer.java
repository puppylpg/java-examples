package example.innerclass;

/**
 * @author liuhaibo on 2023/08/11
 */
public class Outer {

    private int out;

    private int access1() {
        return out;
    }

    private int access2() {
        // 构造内部类的时候，外部类就被传入内部类了。javac干的
        return new Inner().in;
    }

    private class Inner {
        private int in;
        // javac还会多生成这样一个属性，就可以通过它访问创建内部类的外部类的数据了
//        Outer this$0;

        private int access1() {
            // 等同于Outer.this.out;
            return out;
        }

        private int access2() {
            // 其实就是调用this$0.out
            return Outer.this.out;
        }
    }
}
