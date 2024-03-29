package example.proxy.jdk.monitor;

import example.proxy.jdk.Coder;
import example.proxy.jdk.JavaCoder;

import java.lang.reflect.Proxy;

/**
 * @author puppylpg on 2018/11/27
 */
public class Main {

    public static void main(String... args) {
        // 希望被代理的类
        Coder coder = new JavaCoder("Tim");
        // 目标业务类和横且代码类编织到一起
        TimeMonitorHandler handler = new TimeMonitorHandler(coder);
        // 根据上述结合体创建代理实例。即创建揉合了上述两种代码的业务类
        Coder proxy = (Coder) Proxy.newProxyInstance(coder.getClass().getClassLoader(), coder.getClass().getInterfaces(), handler);

        // 调用代理实例
        proxy.implementDemands("Repair computer.");
        proxy.estimateTime("rm -rf /");
    }
}
