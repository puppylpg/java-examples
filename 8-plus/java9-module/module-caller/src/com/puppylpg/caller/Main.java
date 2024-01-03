package com.puppylpg.caller;

import com.puppylpg.callee.Util;
//import com.puppylpg.internal.InternalUtil;

/**
 * @author puppylpg on 2023/08/17
 */
public class Main {
    public static void main(String[] args) {
        new Util().util();

        // callee不exports，caller访问不到
//        new InternalUtil();
    }
}