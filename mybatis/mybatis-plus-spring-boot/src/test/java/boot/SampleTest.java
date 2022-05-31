package boot;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plus.Useruser;
import plus.mapper.UseruserMapper;

import java.util.List;

/**
 * @author puppylpg on 2022/05/31
 */
@SpringBootTest
public class SampleTest {

    @Autowired
    private UseruserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Useruser> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
