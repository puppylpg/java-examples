package example.basic;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * hashcode可能是负的……
 * @author liuhaibo on 2022/10/10
 */
public class HashCodeNegative {

    public static void main(String... args) {
        Map<Integer, Long> counted = IntStream.range(0, 10000000)
                .boxed()
                .map(i -> UUID.randomUUID().hashCode() % 4)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(counted);
    }
}
