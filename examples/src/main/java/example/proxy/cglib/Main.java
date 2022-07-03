package example.proxy.cglib;

import example.proxy.jdk.Coder;
import example.proxy.jdk.JavaCoder;
import net.sf.cglib.proxy.Enhancer;

/**
 * CGlib要比jdk的动态代理舒适很多。因为它：不需要传入被代理的对象。只要把类告诉它，它自己就创建被代理的对象了。
 * 而且，cglib规定：被代理的类必须有默认构造函数，因为它要用这个构造函数生成被代理类。
 * 也正因为如此，cglib只能代理class。如果代理interface，new出来的被代理类无法调用方法，因为它不是个具体的实现类。
 *
 * @author puppylpg on 2022/07/03
 */
public class Main {

    /**
     * >>> Before: 2022-07-03T22:35:46.623
     * 《CGlib has one important restriction: the target class must provide a default constructor》: I'll use 17 hours.
     * <<< After: 2022-07-03T22:35:46.636
     * >>> Before: 2022-07-03T22:35:46.636
     * 《CGlib has one important restriction: the target class must provide a default constructor》: I use Java to finish Send an ad
     * <<< After: 2022-07-03T22:35:46.636
     * >>> Before: 2022-07-03T22:35:46.636
     * <<< After: 2022-07-03T22:35:46.636
     * (CGlib Proxy) Java is perfect!
     */
    public static void main(String ... args) {
        // true coder, 需要被代理
//        Coder coder = new JavaCoder("Tom");

        Enhancer enhancer =  new Enhancer();
        // cglib 不需要传入被代理的类，只需要给出要代理的类的class文件，它自己会创建
        enhancer.setSuperclass(JavaCoder.class);
//        enhancer.setSuperclass(Coder.class);
        enhancer.setCallback(new CGlibBibiAroundInterceptor());
        JavaCoder javaCoder = (JavaCoder) enhancer.create();
//        Coder javaCoder = (JavaCoder) enhancer.create();

        javaCoder.estimateTime("Hello, world");
        javaCoder.implementDemands("Send an ad");

        System.out.println(javaCoder.comment());
    }
}
