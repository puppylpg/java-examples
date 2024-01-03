package example.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * invoke算是反射的告急取代吧。反射获取方法必须先知道方法所在的类。invoke获取方法不需要先提供类，
 * 可以先获取函数签名，到时候再拿着函数签名去某个类里找函数就行了。
 * 这种机制相当于先拿到一个方法mock，等到运行的时候再找到真实的函数，就和动态语言一样了。
 *
 * @author puppylpg on 2024/01/03
 */
public class InvokeDynamic {

	public static void main(String[] args) throws Throwable {
		String str = "abc";
		Integer integer = 123;

		reflection(str, integer);
		invocation(str, integer);
	}

	private static void reflection(String str, Integer integer) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		Method toString = str.getClass().getMethod("toString");

		Method method = Object.class.getMethod("toString");
		System.out.println(method.invoke(str));
		// IllegalArgumentException: object is not an instance of declaring class
//		System.out.println(toString.invoke(integer));

		System.out.println(method.invoke(integer));
	}

	private static void invocation(String str, Integer integer) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// 反射里传的是参数类型的class array；invoke是把return type和parameters type包装成一个method type
		MethodType methodType = MethodType.methodType(String.class);
		// 从超类里找方法
		MethodHandle toStringHandle = lookup.findVirtual(Object.class, "toString", methodType);

		System.out.println(toStringHandle.invoke(str));
		System.out.println(toStringHandle.invoke(integer));
	}
}
