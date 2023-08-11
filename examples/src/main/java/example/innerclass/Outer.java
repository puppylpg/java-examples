package example.innerclass;

/**
 * @author liuhaibo on 2023/08/11
 */
public class Outer {

    private int a;

    private int access1() {
        return a;
    }

    private int access2() {
        return Outer.this.a;
    }

    private class Inner {
        private int b;

        private int access1() {
            return a;
        }

        private int access2() {
            return Outer.this.a;
        }
    }
}
