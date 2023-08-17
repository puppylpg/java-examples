package com.puppylpg.internal;

import com.puppylpg.callee.Main;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

/**
 * 虽然是public，但是不exports出去，外部是无法访问的
 *
 * @author liuhaibo on 2023/08/17
 */
public class InternalUtil {
    public void util() {
        System.out.println("Hello INTERNAL world!");
    }
}
