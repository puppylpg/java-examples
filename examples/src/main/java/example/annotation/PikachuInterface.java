package example.annotation;

public interface PikachuInterface {

    /**
     * 在接口上标记注解并没有什么卵用，并不会被实现类继承
     */
//    @Pikachu()
    void say();
}
