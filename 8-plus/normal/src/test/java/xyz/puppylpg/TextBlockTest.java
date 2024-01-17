package xyz.puppylpg;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author puppylpg on 2024/01/17
 */
public class TextBlockTest {

    @Test
    public void testWithNewLine() {
        String s = """
                hello "world"
                """;

        assertThat(s).isEqualTo("hello \"world\"\n");
    }

    @Test
    public void testWithoutNewLine() {
        String s = """
                hello "world\"""";

        assertThat(s).isEqualTo("hello \"world\"");
    }

    @Test
    public void testSlashEscape() {
        String s = """
                "hello\\world"
                """;

        // hello\world
        assertThat(s).isEqualTo("\"hello\\world\"\n");
    }

    @Test
    public void testSpace() {
        String s1 = """
                    hello
                """;

        assertThat(s1).isEqualTo("    hello\n");

        String s2 = """
                    hello
                    """;

        assertThat(s2).isEqualTo("hello\n");

        // 如果结束的三引号另起一行，那么它在哪个位置，行起点就在哪个位置
        String s3 = """
            hello
            """;

        assertThat(s3).isEqualTo("hello\n");

        String s4 = """
                hello""";

        assertThat(s4).isEqualTo("hello");

        String s5 = """
            hello""";

        assertThat(s5).isEqualTo("hello");

        // 如果结束的三引号不另起一行，那么行起点永远是字母开始的地方
        String s6 = """
                                hello""";

        assertThat(s6).isEqualTo("hello");
    }
}
