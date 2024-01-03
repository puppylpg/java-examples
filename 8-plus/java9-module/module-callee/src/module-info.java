/**
 * @author puppylpg on 2023/08/17
 */module module.callee {
     // 必须export出去，否则caller没法用
    exports com.puppylpg.callee;

    // 让外人访问不到
//    exports com.puppylpg.internal;

    requires java.logging;
    requires commons.lang3;
}