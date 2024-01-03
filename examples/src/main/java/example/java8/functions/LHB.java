package example.java8.functions;

/**
 * @author puppylpg on 2020/07/29
 */
@FunctionalInterface
public interface LHB<R> {

    R generate(String name);
}
