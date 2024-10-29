package top.puppylpg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author puppylpg on 2024/10/29
 */
@ExtendWith(MockitoExtension.class)
public class First {

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
}
