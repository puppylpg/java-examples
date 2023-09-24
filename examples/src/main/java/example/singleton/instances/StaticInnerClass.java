package example.singleton.instances;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author liuhaibo on 2017/12/26
 */
public class StaticInnerClass {

    public static <T> T[] convert(List<T> list, Class<T> type) {
        T[] array = (T[]) Array.newInstance(type, list.size());
        return array;
    }

    private StaticInnerClass() {}

    private static class Holder {
        private static final StaticInnerClass INSTANCE = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return Holder.INSTANCE;
    }

    public String doSomething() {
        return "blabla by " + this.getClass().getSimpleName();
    }
}