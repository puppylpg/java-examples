package top.puppylpg;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * BDD风格：given - when - then
 * BDD的given其实就是原本mockito里的when方法（如果看实现，会发现{@link org.mockito.BDDMockito#given(Object)}的实现就是{@link org.mockito.Mockito#when(Object)}）
 * BDD风格本身有个阶段是when，这里的when指的是“执行时”，不要和mockito里原本的方法when混淆了，这里的when是在“做假设时”，即given阶段。
 *
 * 相对应的，它也有两种写法：
 * - given...willReturn...
 * - willReturn...when...
 *
 * @author puppylpg on 2024/10/30
 */
@ExtendWith(MockitoExtension.class)
public class BehaviorDrivenDevelopmentTest {

    @Mock
    private List<String> mockedList;

    /**
     * BDD和普通test最大的区别在第一步和第三步。
     * 第一步是换种有语义的写法；第二步一模一样；第三步如果不会BDD可以依然用assertj
     *
     * 所以BDD的另一个作用是，多了一种mock的写法。但如果不和BDD风格连起来，似乎没什么卵用……
     */
    @Test
    public void testStubbing() {
        // Given
        // 语义：假设（）会返回（）
        given(mockedList.get(0)).willReturn("first");

        // When
        // 那么当xxx的时候
        String value = mockedList.get(0);

        // Then
        // 会xxx
        assertThat(value).isEqualTo("first");
    }

    /**
     * 同样，对于void方法要反着写，只不过从doXX变成了willXX。
     * 可以参考{@link org.mockito.BDDMockito}的javadoc
     */
    @Test
    public void testMockThrow() {
        // willThrow的实现就是doThrow
        willThrow(new RuntimeException()).given(mockedList).clear();

        assertThatThrownBy(() -> mockedList.clear())
                .isInstanceOf(RuntimeException.class);
    }
}
