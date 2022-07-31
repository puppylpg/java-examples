package example.annotation;

/**
 * 在父类上标注注解，class级别的注解才有可能被子类继承（如果注解里有{@link java.lang.annotation.Inherited}）的话。
 * 方法级别上的注解不会被子类继承。
 *
 * @author puppylpg on 2022/07/31
 */
@Pikachu("parent's annotation")
public class PikachuParent implements PikachuInterface {


    @Pikachu
    @Override
    public void say() {

    }
}
