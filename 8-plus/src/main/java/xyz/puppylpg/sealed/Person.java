package xyz.puppylpg.sealed;

/**
 * @author liuhaibo on 2023/08/10
 */
public sealed interface Person permits Teacher, Coder {

    void say();
}
