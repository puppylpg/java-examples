package xyz.puppylpg.sealed;

/**
 * must be final
 * or sealed with subclass
 *
 * @author puppylpg on 2023/08/10
 */
public final class Teacher implements Person {
    @Override
    public void say() {
        System.out.println("teacher");
    }
}
