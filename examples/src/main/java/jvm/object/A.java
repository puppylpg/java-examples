package jvm.object;

/**
 * @author puppylpg on 2023/08/24
 */
public class A {

    boolean _1byte;

    int _4byte;

    // Ordinary Object Pointers
    Object _oop = new Object();

    // 会被packing到_4byte后面，而非按照declare顺序摆放
    char _2byte;

    Object _oop2 = new Object();
}
