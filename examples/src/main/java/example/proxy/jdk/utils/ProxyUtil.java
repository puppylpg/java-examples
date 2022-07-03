package example.proxy.jdk.utils;

import example.proxy.jdk.JavaCoder;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liuhaibo on 2018/04/19
 */
public class ProxyUtil {

    public static void main(String[] args) throws IOException {
        byte[] classFile = ProxyGenerator.generateProxyClass("DynamicCoder", JavaCoder.class.getInterfaces());
        File file = new File("/tmp/DynamicCoder.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(classFile);
        fos.flush();
        fos.close();
    }
}
