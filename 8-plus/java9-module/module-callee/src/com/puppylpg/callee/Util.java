package com.puppylpg.callee;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

/**
 * @author liuhaibo on 2023/08/17
 */
public class Util {

    public void util() {
        // 这个默认是可以用的，因为在java.base里
        System.out.println("Hello world!");
        // 这个是用不了的，除非：requires java.logging;
        Logger logger = Logger.getLogger(Main.class.getName());

        // 这个也用不了的，除非：requires commons.lang3;
        StringUtils.isNotEmpty("");
    }
}
