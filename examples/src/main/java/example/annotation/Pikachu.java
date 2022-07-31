package example.annotation;

import java.lang.annotation.*;

/**
 * {@link Target}：annotation使用位置；
 * {@link Retention}：注解保留策略（保留到什么时候）。
 *  {@link RetentionPolicy#SOURCE}源文件；
 *  {@link RetentionPolicy#CLASS}字节码，可以理解为最多保留到磁盘；
 *  {@link RetentionPolicy#RUNTIME}可以load到内存，想反射读取解析的话，必须可以load到内存；
 *
 *  {@link Inherited}标注之后，class级别的注解会被子类继承（method和field级别不会）。
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Pikachu {

    String value() default "pikachu";

    String speak() default "pika";
}
