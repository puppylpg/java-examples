package example.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 本质上，我们用cglib写的g(x)和用jdk proxy写g(x)的写法一样，也是在调用被代理类的方法。但是，
 * cglib不需要我们自己传入被代理类，它的MethodProxy，可以直接调用生成的代理类的super class的方法，
 * 也就是被代理类的方法。
 *
 * 这也说明了，cglib生成的代理类，都是被代理类的子类。
 *
 * @author puppylpg on 2022/07/03
 */
public class CGlibBibiAroundInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println(">>> Before: " + LocalDateTime.now());
        // proxy是method的proxy，可以通过invokeSuper调用被代理对象（aka，生成的代理对象的super class）的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("<<< After: " + LocalDateTime.now());
        return "(CGlib Proxy) " + result;
    }
}
