package top.puppylpg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 1. when...doReturn...
 * 2. doReturn...when...：void/spy必须用这个来做，普通方法用哪个都行
 *
 * @author puppylpg on 2024/10/29
 */
@ExtendWith(MockitoExtension.class)
public class MockitoBasicTest {

    /**
     * 需要{@link MockitoExtension}来处理这个注解
     */
    @Mock
    List<String> mockedList;

    @Test
    public void testManuallyMock() {
        // 也可以不用@Mock注解
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        assertThat(mockedList).isNotNull();
    }

    @Test
    public void testRememberInteractions_akaVerify() {
        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    /**
     * "stub"是一个模拟对象行为的术语，stubbing是在告诉一个mock对象在特定方法被调用时该返回什么。
     */
    @Test
    public void testStubbing() {
        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException("a mocked exception"));

        assertThat(mockedList.get(0)).isEqualTo("first");

        // assertj 这样判断异常
        assertThatThrownBy(() -> mockedList.get(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("a mocked exception");

        //following prints "null" because get(999) was not stubbed
        assertThat(mockedList.get(999)).isNull();;

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);
    }

    @Test
    public void testArgumentMatcher() {
        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
//        when(mockedList.contains(argThat(isValid()))).thenReturn(true);

        //following prints "element"
        assertThat(mockedList.get(999)).isEqualTo("element");

        // 上面assert的时候其实就调了一次！
        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        mockedList.add("a string which length is > 5");
        //argument matchers can also be written as Java 8 Lambdas
        verify(mockedList).add(argThat(someString -> someString.length() > 5));
    }

    @Test
    public void testInvokeTimes() {
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void testMockThrow() {
        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        assertThatThrownBy(() -> mockedList.clear())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testConsecutiveCalls() {
        //you can stub consecutive calls
        when(mockedList.get(0))
                .thenReturn("first")
                .thenThrow(new RuntimeException("a mocked exception"))
                .thenReturn("second");

        //prints "first"
        assertThat(mockedList.get(0)).isEqualTo("first");

        //following throws RuntimeException
        assertThatThrownBy(() -> mockedList.get(0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("a mocked exception");

        //prints "second"
        assertThat(mockedList.get(0)).isEqualTo("second");
        //Any consecutive call: prints "second" as well (last stubbing wins).
        assertThat(mockedList.get(0)).isEqualTo("second");

        // 如果不写成链式的，后面的会覆盖前面的stubbing
        when(mockedList.get(0)).thenReturn("1");
        when(mockedList.get(0)).thenReturn("2");
        assertThat(mockedList.get(0)).isEqualTo("2");
    }

    /**
     * Stubbing void methods requires a different approach from when(Object) because the compiler does not like void methods inside brackets...
     * 所以用doXX的原因竟然是编译器不支持括号里写void方法！
     */
    @Test
    public void testStubbingVoid() {
        //stubbing void methods with exceptions
        doThrow(new RuntimeException("boom")).when(mockedList).clear();

        assertThatThrownBy(() -> mockedList.clear())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("boom");
    }

    /**
     * 普通方法也可以用doXX来stub。
     * void/spy必须用doXX来做。
     */
    @Test
    public void testDoReturn() {
        doReturn(false).when(mockedList).add("something");

        assertThat(mockedList.add("something")).isEqualTo(false);
    }

    /**
     * spy就是部分mock（partial mocking），对于没有stub的方法，就会调用实际方法！
     */
    @Test
    public void testSpy() {
        List list = new LinkedList();
        // spy和mock不一样，spy传入的参数是对象；mock传入的参数是类
        List spy = spy(list);

        // 但是这个对spy对象的stub，也没用doXX啊？怎么也生效啊？因为这里调用spy真实的size方法不会直接崩掉
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        assertThat(spy.get(0)).isEqualTo("one");

        //size() method was stubbed - 100 is printed
        assertThat(spy.size()).isEqualTo(100);

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

    @Test
    public void testSpyWithDoReturn() {
        List list = new LinkedList();
        List spy = spy(list);

        // 为什么spy要用doXX来stub？因为下面这个stub会调用真实方法，导致数组越界错误！
        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        assertThatThrownBy(() -> when(spy.get(0)).thenReturn("foo"))
                .isInstanceOf(IndexOutOfBoundsException.class);

        // 所以用doXX来stub spy的方法！
        doReturn("foo").when(spy).get(0);
        assertThat(spy.get(0)).isEqualTo("foo");
    }

    /**
     * spy是部分mock。但是spy并不是通过“delegate”到真实对象来调用真实方法的，
     * spy是原对象的一个copy。
     * Mockito *does not* delegate calls to the passed real instance, instead it actually creates a copy of it. So if you keep the real instance and interact with it, don't expect the spied to be aware of those interaction and their effect on real instance state. The corollary is that when an *un-stubbed* method is called *on the spy* but *not on the real instance*, you won't see any effects on the real instance.
     */
    @Test
    public void testSpyIsACopyOfOriginalObject() {
        List list = new LinkedList();
        List spy = spy(list);

        spy.add(1);

        assertThat(spy.size()).isEqualTo(1);
        assertThat(list.size()).isEqualTo(0);
    }

//    @Test
//    public void testMockStatic() {
//        assertThat(Collections.emptyList()).size().isEqualTo(0);
//
//        try (MockedStatic mocked = mockStatic(Collections.class)) {
//            mocked.when(Collections::emptyList).thenReturn(List.of(123));
//
//            assertThat(Collections.emptyList()).size().isEqualTo(1)
//                    .returnToIterable().containsExactlyElementsOf(List.of(123));
//        }
//    }

}
