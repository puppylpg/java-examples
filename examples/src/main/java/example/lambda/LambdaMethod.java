package example.lambda;

/**
 * @author puppylpg on 2024/01/11
 */
public class LambdaMethod {

    public static void main(String... args) {
        String s = "abc";

        // indexOf
        System.out.println(s.indexOf("c", 1));

        Invokable invokable = s::indexOf;
        System.out.println(invokable.invoke("c", 1));

        // lastIndexOf
        System.out.println(s.lastIndexOf("b", 1));

        Invokable invokable2 = s::lastIndexOf;
        System.out.println(invokable2.invoke("b", 1));
    }

    /**
     * 表示一个方法
     */
    interface Invokable {

        /**
         * 用它来表示任何参数为Stirng，int的方法，比如{@link String#indexOf(String, int)}，
         * {@link String#lastIndexOf(String, int)}
         */
        int invoke(String str, int fromIndex);
    }
}
