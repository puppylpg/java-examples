package example.generic;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author puppylpg on 2021/12/30
 */
public class TypeReferenceDemo {

    public static void main(String... args) {
        TypeReference<List<String>> type = new TypeReference<List<String>>() {
        };
        System.out.println(type.getType());
        ParameterizedType parameterizedType = (ParameterizedType) type.getType();
        System.out.println(parameterizedType.getRawType());
        System.out.println(parameterizedType.getActualTypeArguments()[0]);

        System.out.println(type.getClass().getGenericSuperclass());
    }
}
