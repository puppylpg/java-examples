package example.properties;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author puppylpg on 2018/07/09
 */
public class CliProperty {

    /**
     * 1. vm options:    -Dpuppylpg.love=liubobo-FOREVER
     * 2. java -Dpuppylpg.love=liubobo-forever -cp target/examples-1.0-SNAPSHOT.jar example.properties.CliProperty
     *
     * -Dproperty=value
     *      Sets a system property value.
     *      The property variable is a string with no spaces that represents the name of the property.
     *      The value variable is a string that represents the value of the property.
     *      If value is a string with spaces, then enclose it in quotation marks (for example -Dfoo="foo bar").
     *
     * @param args args
     */
    public static void main(String ... args) {
        System.out.println(System.getProperty("puppylpg.love"));
        printSystemProperties();
        printSystemEnvironments();
    }

    private static void printSystemProperties() {
        System.out.println("===== Properties =====");
        Properties properties = System.getProperties();
        Set<String> keys = properties.stringPropertyNames();
        keys.forEach(key -> System.out.println(key + "=" + properties.get(key)));
    }

    private static void printSystemEnvironments() {
        System.out.println("===== Environments =====");
        Map<String, String> environments = System.getenv();
        environments.forEach((key, value) -> System.out.println(key + "=" + value));
    }
}
