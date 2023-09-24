package xyz.puppylpg;

import java.util.Arrays;

/**
 * @author liuhaibo on 2023/08/10
 */
public class SwitchExpression {

    public static void main(String... args) {
        Arrays.asList(old("2"), newSwitchExpression("2"), switchCaseOld(6))
                .forEach(System.out::println);
    }

    private static int old(String x) {
        int i;

        switch (x) {
            case "1":
                i = 1;
                break;
            case "2":
                i = 2;
                break;
            default:
                i = x.length();
                break;
        }
        return i;
    }

    private static int newSwitchExpression(String x) {
        int i = switch (x) {
            case "1" -> 1;
            case "2" -> 2;
            default -> x.length();
        };
        return i;
    }

    private static String switchCaseOld(Object o) {
        String formatted = "unknown";
        if (o instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (o instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (o instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (o instanceof String s) {
            formatted = String.format("String %s", s);
        }
        return formatted;
    }

    /**
     * java: switch 语句中的模式 是预览功能，默认情况下禁用。
     *   （请使用 --enable-preview 以启用 switch 语句中的模式）
     */
//    private static String switchCaseNew(Object o) {
//        return switch (o) {
//            case Integer i -> String.format("int %d", i);
//            case Long l    -> String.format("long %d", l);
//            case Double d  -> String.format("double %f", d);
//            case String s  -> String.format("String %s", s);
//            default        -> "unknown";
//        };
//    }
}
