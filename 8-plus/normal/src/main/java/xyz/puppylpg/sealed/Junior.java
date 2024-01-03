package xyz.puppylpg.sealed;

/**
 * no subclass, must be final
 *
 * @author puppylpg on 2023/08/10
 */
public final class Junior extends Coder {

    @Override
    public void say() {
        System.out.println("i can't code");
    }
}
