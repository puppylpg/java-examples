package xyz.puppylpg.sealed;

/**
 * use sealed, still have subclass
 *
 * @author liuhaibo on 2023/08/10
 */
public sealed class Coder implements Person permits Junior {
    @Override
    public void say() {
        System.out.println("coder");
    }
}
