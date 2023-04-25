package example.generic;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map/List怎么获取他们实际的类型的，TypeReference就是怎么做的，只不过用{@link TypeReference#getType()}封装了一下
 *
 * @author puppylpg on 2021/12/30
 */
public class TypeReferenceDemo {

    public static void main(String... args) {

        Map<String, Integer> map = new HashMap<>() {
        };
        // class example.generic.TypeReferenceDemo$1
        System.out.println(map.getClass());
        // java.util.HashMap<java.lang.String, java.lang.Integer>
        System.out.println(map.getClass().getGenericSuperclass());
        ParameterizedType mapParameterizedType = (ParameterizedType) map.getClass().getGenericSuperclass();
        // class java.util.HashMap
        System.out.println(mapParameterizedType.getRawType());
        // [class java.lang.String, class java.lang.Integer]
        System.out.println(Arrays.toString(mapParameterizedType.getActualTypeArguments()));
        // class java.lang.String
        System.out.println(mapParameterizedType.getActualTypeArguments()[0]);
        // class java.lang.Integer
        System.out.println(mapParameterizedType.getActualTypeArguments()[1]);

        TypeReference<List<String>> typeReference = new TypeReference<>() {
        };
        // class example.generic.TypeReferenceDemo$2
        System.out.println(typeReference.getClass());
        // 此处不需要(ParameterizedType) typeReference.getClass().getGenericSuperclass()，已经封装到getType方法里了
        ParameterizedType parameterizedType = (ParameterizedType) typeReference.getType();
        // java.util.List<java.lang.String>
        System.out.println(typeReference.getType());
        // interface java.util.List
        System.out.println(parameterizedType.getRawType());
        // class java.lang.String
        System.out.println(parameterizedType.getActualTypeArguments()[0]);

        // com.fasterxml.jackson.core.type.TypeReference<java.util.List<java.lang.String>>
        System.out.println(typeReference.getClass().getGenericSuperclass());
    }
}
